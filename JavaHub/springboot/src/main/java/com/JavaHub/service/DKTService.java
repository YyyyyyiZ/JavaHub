package com.JavaHub.service;

import com.JavaHub.entity.ExerRecord;
import com.JavaHub.entity.Exercise;
import com.JavaHub.entity.dto.ExerrecordDTO;
import com.JavaHub.mapper.ExerRecordMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;

@Service
public class DKTService extends ServiceImpl<ExerRecordMapper, ExerRecord> {


    @Resource
    private ExerRecordMapper exerRecordMapper;


    public static void DKT() {
        Process proc;
        try {
            //String exe = "D:\\Python\\python";
            String exe = "/www/wwwroot/study-master/202cb962ac59075b964b07152d234b70_venv/bin/python3";//华为云
            //String command = "E:\\javahub\\study-master\\DKT\\main.py";
            String command = "/www/wwwroot/study-master/DKT/main.py";//华为云
            String[] cmdArr = new String[] {exe, command};
            proc = Runtime.getRuntime().exec(cmdArr);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"utf-8"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public List<Exercise> newTest(String userid,String chapid){

        System.out.println(userid+" "+chapid);
        List<Exercise> lfinal = Merge(userid,chapid);
        return lfinal;
    }

    public List<Exercise> Merge(String userid, String chapid){


        //选择该章节下用户对应的习题记录和考试记录,exerid,exerdate,rightwrong
        List<ExerrecordDTO> exerRecord = exerRecordMapper.exerrecord(userid,chapid);
        List<ExerrecordDTO> testRecord = exerRecordMapper.testrecord(userid,chapid);
        List<ExerrecordDTO> allRecord = mergeExercise(exerRecord,testRecord);

        //时间序列的日期
        List<String> exerdate = exerRecordMapper.exerdate(userid,chapid);
        List<String> testdate = exerRecordMapper.testdate(userid,chapid);
        List<String> alldate = mergeDate(exerdate,testdate);
        Collections.sort(alldate);

        List<List<Integer>> exerid = new ArrayList<>();
        List<List<Integer>> rightwrong = new ArrayList<>();
        for (String oneDay:alldate){
            List<Integer> oneDayExer = new ArrayList<>();
            List<Integer> oneDayRightWrong = new ArrayList<>();
            for (ExerrecordDTO oneExer:allRecord){
                if (oneExer.getExerdate().equals(oneDay)){
                    oneDayExer.add(oneExer.getExerid());
                    if (oneExer.getRightwrong().equals("Y")){
                        oneDayRightWrong.add(1);
                    } else if (oneExer.getRightwrong().equals("N")) {
                        oneDayRightWrong.add(0);
                    }
                }
            }
            exerid.add(oneDayExer);
            rightwrong.add(oneDayRightWrong);
        }
        Map<Integer,List<List<Integer>>> map = Exer2Know(exerid,rightwrong,chapid,allRecord);
        Map2CSV(map,"data.csv");
        DKT();
        //读取预测错误率
        List<String> raw = readCsv("predict.csv");
        List<Integer> knowids = new ArrayList<>();
        List<Double> probs = new ArrayList<>();
        String[] temp0 = raw.get(0).split(",");
        String[] temp1 = raw.get(1).split(",");

        for (int i=0;i<temp0.length;i++){
            knowids.add(Integer.parseInt(Pattern.compile("[^0-9]").matcher(temp0[i]).replaceAll("")));
            probs.add(Double.parseDouble(temp1[i].trim()));
        }
        //对于没有涉及到过的知识点，假设掌握度为0，即预测错误率为1
        List<Integer> allKnowids = exerRecordMapper.selectKnowids(chapid);
        for (Integer knowid:knowids){
            if (!knowids.contains(knowid)){
                knowids.add(knowid);
                probs.add(1.0);
            }
        }
        List<Integer> finalExerids = score(knowids,probs,chapid,userid);
        return lfinal(finalExerids);

    }

    public List<Exercise> lfinal(List<Integer> finalExerids){

        List<Exercise> lfinal= new ArrayList<>();
        //题量不够
        for (int i=0;i<15;i++){
            Integer exerid = finalExerids.get(i);
            Exercise exercise = exerRecordMapper.selectExercise(exerid);
            lfinal.add(exercise);
        }
        return lfinal;
    }

    public List<Integer> score(List<Integer> knowids,List<Double> probs,String chapid,String userid){

        Map<Integer,Double> know_prob=new HashMap<>();
        for (int i=0;i< knowids.size();i++){
            know_prob.put(knowids.get(i),probs.get(i));
        }

        //该章节下所有做过的exerid
        List<Integer> doneExerids = exerRecordMapper.selectDoneEexrids(userid,chapid);
        //该章节下所有的exerid
        List<Integer> allExerids = exerRecordMapper.selectAllExerids(chapid);
        List<Double> scores = new ArrayList<>();
        //知识点的到题目的映射
        Map<Integer,List<Integer>> matrix2 = new HashMap<>();
        List<ExerrecordDTO> KnowExer = exerRecordMapper.selectMatrix(chapid);
        for (Integer oneExerid:allExerids){
            List<Integer> temp = new ArrayList<>();
            for (ExerrecordDTO dto:KnowExer){
                if (dto.getExerid()==oneExerid){
                    temp.add(dto.getKnowid());
                }
            }
            matrix2.put(oneExerid,knowids);
        }
        //每道题打分
        Map<Integer,Double> exer_prob = new HashMap<>();
        for (int j=0;j<allExerids.size();j++){
            List<Integer> temp_knowids =matrix2.get(allExerids.get(j));
            Double oneScore = 0.0;
            for (Integer temp_oneKnowid:temp_knowids){
                oneScore+=know_prob.get(temp_oneKnowid);
            }
            //如果此题已经做过，选到的概率减半
            if (doneExerids.contains(allExerids.get(j))){
                oneScore=oneScore*0.5;
            }
            scores.add(oneScore);
            exer_prob.put(allExerids.get(j),oneScore);
        }
        //取得分最高的前20个
        List<Map.Entry<Integer,Double>> lstEntry=new ArrayList<>(exer_prob.entrySet());
        Collections.sort(lstEntry,(Comparator.comparing(Map.Entry::getValue)));
        List<Integer> finalExerids = new ArrayList<>();
        lstEntry.forEach(o->{
            System.out.println(o.getKey()+":"+o.getValue());
            finalExerids.add(o.getKey());
        });
        return finalExerids;
    }

    public Map<Integer,List<List<Integer>>> Exer2Know(List<List<Integer>> exerid,List<List<Integer>> rightwrong,String chapid,List<ExerrecordDTO> allRecord){

        //每道题的对错
        Map<Integer,List<Integer>> exer_rightwrongMap = new HashMap<>();
        int i=0;
        for (int m=0;m<exerid.size();m++){
            for (int n=0;n<exerid.get(m).size();n++){
                List<Integer> temp = new ArrayList<>();
                temp.add(exerid.get(m).get(n));
                temp.add(rightwrong.get(m).get(n));
                exer_rightwrongMap.put(i,temp);
                i++;
            }
        }

        //题目到知识点的映射
        Map<Integer,List<Integer>> matrix1 = new HashMap<>();
        List<ExerrecordDTO> KnowExer = exerRecordMapper.selectMatrix(chapid);
        List<Integer> exerids = new ArrayList<>();
        for (ExerrecordDTO dto:allRecord){
            if (!exerids.contains(dto.getExerid())){
                exerids.add(dto.getExerid());
            }
        }
        for (Integer oneExerid:exerids){
            List<Integer> knowids = new ArrayList<>();
            for (ExerrecordDTO dto:KnowExer){
                if (dto.getExerid()==oneExerid){
                    knowids.add(dto.getKnowid());
                }
            }
            matrix1.put(oneExerid,knowids);
        }


        List<List<Integer>> allKnows=new ArrayList<>();
        List<List<Integer>> allRightwrongs=new ArrayList<>();
        List<Integer> dailyLength = new ArrayList<>();
        int index=0;
        //对于某一天的所有题
        for (List<Integer> oneDayExers:exerid){
            List<Integer> oneDayknows=new ArrayList<>();
            List<Integer> oneDayrightwrongs=new ArrayList<>();
            //对于某一天的某一道题
            for (Integer oneExer:oneDayExers){
                //该题的对错
                Integer temp_rightwrong = exer_rightwrongMap.get(index).get(1);
                //该题对应的所有知识点
                List<Integer> temp = matrix1.get(oneExer);
                for (Integer oneKnowid:temp){
                    oneDayknows.add(oneKnowid);
                    oneDayrightwrongs.add(temp_rightwrong);
                }
                index++;
            }
            Integer oneDayLength = oneDayknows.size();
            dailyLength.add(oneDayLength);
            allKnows.add(oneDayknows);
            allRightwrongs.add(oneDayrightwrongs);
        }
        Map<Integer,List<List<Integer>>> map = new HashMap<>();
        for (int k=0;k<dailyLength.size();k++){
            List<List<Integer>> wrap=new ArrayList<>();
            wrap.add(allKnows.get(k));
            wrap.add(allRightwrongs.get(k));
            map.put(k,wrap);
        }
        return map;
    }

    //导出到csv文件
    public static void Map2CSV(Map<Integer,List<List<Integer>>> data, String path) {
        try {
            BufferedWriter out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
            Set<Integer> keys = data.keySet();
            System.out.println(keys);
            for (Integer oneKey:keys)
            {
                String temp = String.valueOf(data.get(oneKey).get(0).size());
                out.write(temp);
                System.out.println(oneKey);
                out.newLine();
                for (List onerow:data.get(oneKey)){
                    System.out.println(onerow);
                    for (int j = 0; j < onerow.size(); j++)
                    {
                        out.write(onerow.get(j).toString());
                        out.write(",");
                    }
                    out.newLine();
                }
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //exerdate和lerandate取并集
    public List<String> mergeDate(List<String> list1,List<String> list2){

        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
        return listAllDistinct;

    }

    public List<ExerrecordDTO> mergeExercise(List<ExerrecordDTO> list1,List<ExerrecordDTO> list2){

        List<ExerrecordDTO> listAll = list1.parallelStream().collect(toList());
        List<ExerrecordDTO> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        List<ExerrecordDTO> listAllDistinct = listAll.stream().distinct().collect(toList());
        return listAllDistinct;

    }

    public static List<String> readCsv(String filePath) {

        File csv = new File(filePath);
        csv.setReadable(true);
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            isr = new InputStreamReader(new FileInputStream(csv), "UTF-8");
            br = new BufferedReader(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String line = "";
        List<String> records = new ArrayList<>();
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                records.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

}
