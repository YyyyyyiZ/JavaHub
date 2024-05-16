<template>
  <div>
    <!--      功能区域-->
    <div style="margin: 15px;">
      <el-button @click="add">新增</el-button>
      <el-upload action="/api/exercise/import" :show-file-list=false accept="xlsx" :on-success="ImportSuccess" style="display:inline-block">
        <el-button class="ml-5">导入</el-button>
      </el-upload>
      <el-button @click="exp" class="ml-5">导出</el-button>
    </div>
    <!--      搜索区域-->
    <div style="margin: 15px">
      <el-input v-model="search" placeholder="请输入内容" style="width:25%" clearable></el-input>
    </div>
    <div>
      <el-table :data="tableData.slice((paginations.currentPage-1)*paginations.PageSize,paginations.currentPage*paginations.PageSize)" border stripe style="width: 100vw ;margin:10px">
        <el-table-column prop="exerid" label="exerid" sortable />
        <el-table-column prop="exertype" label="exertype"  />
        <el-table-column prop="exerques" label="exerques"  />
        <el-table-column prop="exerans" label="exerans"  />
        <!--        <el-table-column prop="grade" label="grade" />-->
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="mini" @click="Edit(scope.row)">编辑</el-button>
            <el-popconfirm title="是否确认删除？" @confirm="Delete(scope.row.exerid)">
              <template #reference>
                <el-button size="mini" type="danger">删除</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin:15px">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="paginations.currentPage"
            :page-sizes="paginations.pageSizes"
            :page-size="paginations.PageSize"
            layout="total,sizes,prev,pager,next,jumper"
            :total="tableData.length">
        </el-pagination>
      </div>
    </div>
    <!--      编辑-->
    <el-dialog title="更改习题" v-model="dialogVisible1" width="30%" >
      <el-form :model="form" label-width="70px">
        <el-form-item label="习题ID" >
          <el-input v-model="form.exerid" autocomplete="off" style="width:80%" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="习题类型" >
          <el-input v-model="form.exertype" autocomplete="off" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="习题内容" >
          <el-input v-model="form.exerques" autocomplete="off" style="width:80%"></el-input>
        </el-form-item>
        <el-form-item label="习题答案" >
          <el-input v-model="form.exerans" autocomplete="off" style="width:80%"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible1 = false">取 消</el-button>
          <el-button type="primary" @click="dialogUpdateConfirm">确 定</el-button>
        </span>
      </template>
    </el-dialog>

    <!--      新增-->
    <Dialog header="新增习题" v-model:visible="dialogVisible" :breakpoints="{'960px': '50vw', '640px': '75vw'}"
            :style="{width: '40vw'}">
      <TabView ref="tabview1">
        <TabPanel header="单选题">
          <p>题目</p>
          <Textarea v-model="SingleQues" rows="3" cols="58" autocomplete="off"/>
          <Divider/>
          <p>选项</p>
          <div class="grid p-fluid">
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                <span class="p-inputgroup-addon"><Checkbox v-model="SingleChecked1" :binary="true" autocomplete="off"/></span>
                <InputText v-model="A1" placeholder="选项A" autocomplete="off"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="SingleChecked2" :binary="true" autocomplete="off"/>
                    </span>
                <InputText v-model="A2" placeholder="选项B" autocomplete="off"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="SingleChecked3" :binary="true" autocomplete="off"/>
                    </span>
                <InputText v-model="A3" placeholder="选项C" autocomplete="off"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="SingleChecked4" :binary="true" autocomplete="off"/>
                    </span>
                <InputText v-model="A4" placeholder="选项D" autocomplete="off"/>
              </div>
            </div>
          </div>
          <p>关联知识点</p>
          <el-input v-model="knowid" autocomplete="off" style="width:40%" ></el-input>
        </TabPanel>

        <TabPanel header="多选题">
          <p>题目</p>
          <Textarea v-model="MultiQues" rows="3" cols="58" autocomplete="off"/>
          <Divider/>
          <p>选项</p>
          <ScrollPanel style="width: 100%; height: 200px" class="custombar1">
            <div class="grid p-fluid">
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked1" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B1" placeholder="选项A" autocomplete="off"/>
                </div>
              </div>
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked2" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B2" placeholder="选项B" autocomplete="off"/>
                </div>
              </div>
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked3" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B3" placeholder="选项C" autocomplete="off"/>
                </div>
              </div>
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked4" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B4" placeholder="选项D" autocomplete="off"/>
                </div>
              </div>
            </div>
            <div class="grid p-fluid">
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked5" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B5" placeholder="选项E" autocomplete="off"/>
                </div>
              </div>
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox  v-model="checked6" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B6" placeholder="选项F" autocomplete="off"/>
                </div>
              </div>
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox  v-model="checked7" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B7" placeholder="选项G" autocomplete="off"/>
                </div>
              </div>
              <div class="field col-12 md:col-6">
                <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox  v-model="checked8" :binary="true" autocomplete="off"/>
                    </span>
                  <InputText v-model="B8" placeholder="选项H" autocomplete="off"/>
                </div>
              </div>
            </div>
          </ScrollPanel>
          <p>关联知识点</p>
          <el-input v-model="knowid" autocomplete="off" style="width:40%" ></el-input>
        </TabPanel>
        <TabPanel header="判断题">
          <p>题目</p>
          <Textarea v-model="JudgeQues" rows="5" cols="58" autocomplete="off"/>
          <Divider/>
          <p>答案</p>
          <div class="field-radiobutton">
            <el-radio v-model="C" label="T" autocomplete="off">正确</el-radio>
<!--            <RadioButton inputId="city1" name="city" value="Chicago" v-model="T"/>-->
<!--            <label v-model="A" for="city1">正确</label>-->
          </div>
          <div class="field-radiobutton">
            <el-radio v-model="C" label="F" autocomplete="off">错误</el-radio>
<!--            <RadioButton inputId="city2" name="city" value="Los Angeles" v-model="T"/>-->
<!--            <label v-model="A" for="city2">错误</label>-->
          </div>
          <p>关联知识点</p>
          <el-input v-model="knowid" autocomplete="off" style="width:40%" ></el-input>
        </TabPanel>
        <TabPanel header="填空题">
          <p>题目</p>
          <Textarea v-model="FillQues" rows="3" cols="58" autocomplete="off"/>
          <Divider/>
          <p>答案</p>
          <Textarea v-model="FillAns" rows="1" cols="58" autocomplete="off"/>
          <p>关联知识点</p>
          <el-input v-model="knowid" autocomplete="off" style="width:40%" ></el-input>
        </TabPanel>
      </TabView>
      <template #footer>
        <Button label="取消" icon="pi pi-times" @click="dialogVisible = false" class="p-button-text"/>
        <Button label="新增" icon="pi pi-check" @click="addExer" autofocus/>
      </template>
    </Dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import {ref} from "vue";

export default {
  name: "Exerad",
  data(){
    return{
      form:{},
      dialogVisible:false,//控制新增弹出框的显示与否
      dialogVisible1: false,//控制更新弹出框的显示与否
      search:'',
      SingleQues:"",
      MultiQues:"",
      JudgeQues:"",
      FillQues:"",
      FillAns:"",
      C:"",
      knowid:"",
      paginations:{
        // 默认显示第几页
        currentPage:1,
        // 个数选择器（可修改）
        pageSizes:[5,10,15],
        // 默认每页显示的条数（可修改）
        PageSize:5,
      },
      tableData:[],
      tableDataAnother:[],
    }
  },
  watch: {
    search(newVal) {
      // console.log(newVal);
      this.tableDataAnother=this.tableData
      // const fuzzy = this.search;
      if(newVal){
        this.tableDataAnother = this.tableDataAnother.filter((item) => {
          var reg = new RegExp(newVal, "gi");
          return reg.test(item.exerques)
        });
        this.tableData = this.tableDataAnother;
      }
      else{
        this.load();
      }
    }
  },
  setup() {
    const SingleChecked1 = ref(false);
    const SingleChecked2 = ref(false);
    const SingleChecked3 = ref(false);
    const SingleChecked4 = ref(false);
    const A1=ref('');
    const A2=ref('');
    const A3=ref('');
    const A4=ref('');
    const checked1 = ref(false);
    const checked2 = ref(false);
    const checked3 = ref(false);
    const checked4 = ref(false);
    const checked5 = ref(false);
    const checked6 = ref(false);
    const checked7 = ref(false);
    const checked8 = ref(false);
    const B1=ref('');
    const B2=ref('');
    const B3=ref('');
    const B4=ref('');
    const B5=ref('');
    const B6=ref('');
    const B7=ref('');
    const B8=ref('');
    // const T = ref('');
    return {SingleChecked1, SingleChecked2, SingleChecked3, SingleChecked4, A1,A2,A3,A4,
      checked1, checked2, checked3, checked4,checked5, checked6, checked7, checked8,
    B1,B2,B3,B4,B5,B6,B7,B8}
  },
  created() {
    this.load()
    // this.tableDataAnother=this.tableData
  },
  methods:{
    load(){
      // 发送axios请求  $http是封装的接口地址
      request.get("/exercise/exerad").then(res=>{
        // 把用户信息赋值给tableData
        console.log(res)
        this.tableData=res;
      })
    },
    // 新增对话框
    add(){
      this.dialogVisible = true
      this.form = {}
    },
    addExer(){
      // console.log(this.A)
      // console.log(this.SingleChecked1,this.SingleChecked2,this.SingleChecked3,this.SingleChecked4)
      var exques;
      var exid;
      var extype;
      var exans;
      var knowid;
      // 单选
      if(this.SingleQues!=""){
        knowid=this.knowid;
        exques=this.SingleQues;
        if(this.A1!=''){
          exques+="~"+this.A1;
        }
        if(this.A2!=''){
          exques+="~"+this.A2;
        }
        if(this.A3!=''){
          exques+="~"+this.A3;
        }
        if(this.A4!=''){
          exques+="~"+this.A4;
        }
        if(this.SingleChecked1){
          exans=this.A1;
        }else if(this.SingleChecked2){
          exans=this.A2;
        }else if(this.SingleChecked3){
          exans=this.A3;
        }else if(this.SingleChecked4){
          exans=this.A4;
        }
        exid=this.tableData.length+1;
        extype="SingleCh";
        this.SingleQues="";this.A1="";this.A2="";this.A3="";this.A4="";this.SingleChecked1=false;this.SingleChecked2=false;this.SingleChecked3=false;this.SingleChecked4=false;
      }
      // 多选
      else if(this.MultiQues!=""){
        knowid=this.knowid;
        exques=this.MultiQues;
        exans="";
        if(this.B1!=''){
          exques+="~"+this.B1;
        }
        if(this.B2!=''){
          exques+="~"+this.B2;
        }
        if(this.B3!=''){
          exques+="~"+this.B3;
        }
        if(this.B4!=''){
          exques+="~"+this.B4;
        }
        if(this.B5!=''){
          exques+="~"+this.B5;
        }
        if(this.B6!=''){
          exques+="~"+this.B6;
        }
        if(this.B7!=''){
          exques+="~"+this.B7;
        }
        if(this.B8!=''){
          exques+="~"+this.B8;
        }
        if(this.checked1&&exans==""){
          exans=this.B1;
        }else if(this.checked1){
          exans+="~"+this.B1;
        }
        if(this.checked2&&exans==""){
          exans=this.B2;
        }else if(this.checked2){
          exans+="~"+this.B2;
        }
        if(this.checked3&&exans==""){
          exans=this.B3;
        }else if(this.checked3){
          exans+="~"+this.B3;
        }
        if(this.checked4&&exans==""){
          exans=this.B4;
        }else if(this.checked4){
          exans+="~"+this.B4;
        }
        if(this.checked5&&exans==""){
          exans=this.B5;
        }else if(this.checked5){
          exans+="~"+this.B5;
        }
        if(this.checked6&&exans==""){
          exans=this.B6;
        }else if(this.checked6){
          exans+="~"+this.B6;
        }
        if(this.checked7&&exans==""){
          exans=this.B7;
        }else if(this.checked7){
          exans+="~"+this.B7;
        }
        if(this.checked8&&exans==""){
          exans=this.B8;
        }else if(this.checked8){
          exans+="~"+this.B8;
        }
        exid=this.tableData.length+1;
        extype="MultiCh";
        this.MultiQues="";this.B1="";this.B2="";this.B3="";this.B4="";this.B5="";this.B6="";this.B7="";this.B8="";
        this.checked1=false;this.checked2=false;this.checked3=false;this.checked4=false;this.checked5=false;this.checked6=false;this.checked7=false;this.checked8=false;
      }
      // 判断
      else if(this.JudgeQues!=""){
        knowid=this.knowid;
        exques=this.JudgeQues;
        exans=this.C;
        exid=this.tableData.length+1;
        extype="Judge";
        this.JudgeQues="";this.C="";
      }
      // 填空
      else if(this.FillQues!=""){
        knowid=this.knowid;
        exques=this.FillQues;
        exans=this.FillAns;
        extype="Fill";
        exid=this.tableData.length+1;
        this.FillQues="";this.FillAns="";
      }
      request.post("/exercise/exerad/"+exid+"/"+extype+"/"+exques+"/"+exans).then(res=>{
        console.log(res)
        this.load()
      })
      request.post("/exercise/exerad/"+knowid+"/"+exid).then(res=>{
        console.log(res)
        console.log(exid)
      })
      this.dialogVisible=false
    },
    Edit(row){
      console.log(row.exerid);//获取当前编辑学生的学号
      this.dialogVisible1= true,
          this.form.exerid=row.exerid,
          this.form.exertype=row.exertype,
          this.form.exerques=row.exerques,
          this.form.exerans=row.exerans
    },
    Delete(exerid){
      // console.log(exerid)
      request.delete("/exercise/exerad/" + exerid).then(
          res=>{
            console.log(res)
            this.load()
          }
      )
    },
    dialogUpdateConfirm(){
      request.put("/exercise/exerad", this.form).then(res=>{
        console.log(res)
        this.load()
      })
      this.dialogVisible1=false
    },
    handleSizeChange(val){
      this.paginations.PageSize=val
      // 注意：在改变每页显示的条数时，要将页码显示到第一页
      this.paginations.currentPage=1
      console.log(`每页 ${val} 条`);
    },
    handleCurrentChange(val){
      this.paginations.currentPage=val
      console.log(`当前页: ${val}`);
    },
    exp(){
      window.open("/api/exercise/export")
    },
    ImportSuccess(){
      this.$message.success("导入成功")
      this.load()
    },
  },
}
</script>

<style lang="scss" scoped>
::v-deep(.p-scrollpanel) {
  p {
    padding: .5rem;
    line-height: 1.5;
    margin: 0;
  }

  &.custombar1 {
    .p-scrollpanel-wrapper {
      border-right: 5px solid var(--surface-ground);
    }

    .p-scrollpanel-bar {
      background-color: var(--primary-color);
      opacity: 1;
      transition: background-color .2s;

      &:hover {
        background-color: #007ad9;
      }
    }
  }
}
</style>