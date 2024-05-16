<template>
  <Toast position="top-center"/>
  <div>
    <h5>第{{chapid}}章 {{chapname}}</h5>
    <div class="card">
      <Fieldset legend="单选题" :toggleable="true">
        <SingleCh v-for="one in singleType" :data="one" :key="one.exerid"  ref="one" @answer="submit2"></SingleCh>
        </Fieldset>
    </div>
    <div class="card">
      <Fieldset legend="多选题" :toggleable="true">
        <MultiCh v-for="one in multiType" :data="one" :key="one.exerid" ref="two" @answer="submit2"></MultiCh>
        </Fieldset>
    </div>
    <div class="card">
      <Fieldset legend="填空题" :toggleable="true">
        <Fill v-for="one in fillType" :data="one" :key="one.exerid" ref="three" @answer="submit2"></Fill>
        </Fieldset>
    </div>
    <div class="card">
      <Fieldset legend="选择题" :toggleable="true">
        <Judge v-for="one in judgeType" :data="one" :key="one.exerid" ref="four" @answer="submit2"></Judge>
        </Fieldset>
    </div>
    <Toolbar class="mb-4">
      <template v-slot:start>
        <div class="my-1">
          <Button class="p-button-rounded p-button-text" disabled><Timer ref="timer" @time="getTime"></Timer></Button>
        </div>
      </template>
      <template v-slot:end>
        <div class="my-1">
          <Button label="提交" icon="pi pi-check-circle" class="p-button mr-3" @click="submit"/>
        </div>
      </template>
    </Toolbar>
  </div>
</template>

<script>
import Timer from "@/components/Timer";
import SingleCh from "@/pattern/SingleCh";
import MultiCh from "@/pattern/MultiCh";
import Fill from "@/pattern/Fill";
import Judge from "@/pattern/Judge";
import router from "@/router";

export default {
  data() {
    return {
      testTime:null,
      data:null,
      singleType:null,
      multiType:null,
      fillType:null,
      judgeType:null,
      rightwrong:[],
      index:0,
      score:0,
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      chapname:localStorage.getItem("chapname"),
      chapid:localStorage.getItem("chapid"),
      }
  },
  components:{
    'Timer':Timer,
    'SingleCh':SingleCh,
    'MultiCh':MultiCh,
    'Fill':Fill,
    'Judge':Judge,
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
    let obj=JSON.stringify({
      "userid":this.user.userid,
      "chapid":this.chapid
    })
    this.request.post("/test/newTest",obj).then(res => {
      this.data=res
      this.singleType=res[0]
      this.multiType=res[1]
      this.fillType=res[2]
      this.judgeType=res[3]
    })
  },
  methods:{
    getTime(time){
      this.testTime=time/60
    },
    submit(){
      this.$refs.timer.reload()
      let answer=[]
      let i=0
      let compo=document.getElementsByClassName("p-fieldset-content")
      for (let j=0;j<compo[0].children.length;j++,i++){
        let one=compo[0].getElementsByClassName("p-checkbox p-component p-checkbox-checked")[j].parentElement.innerText
        this.assess1(one,j,i)
        answer[i]=one
      }
      for (let j=0;j<compo[1].children.length;j++,i++){
        let temp=[]
        for (let k=0;k<compo[1].children[j].getElementsByClassName("p-checkbox p-component p-checkbox-checked").length;k++){
          let part=compo[1].children[j].getElementsByClassName("p-checkbox p-component p-checkbox-checked")[k].parentElement.innerText
          temp[k]=part
        }
        this.assess2(temp,j,i)
        answer[i]=temp
      }
      for (let j=0;j<compo[2].children.length;j++,i++){
        let one=compo[2].getElementsByClassName("p-inputtext p-component p-filled")[j].value
        answer[i]=one
        this.assess3(one,j,i)
      }
      for (let j=0;j<compo[3].children.length;j++,i++){
        let one=compo[3].getElementsByClassName("p-checkbox p-component p-checkbox-checked")[j].parentElement.innerText
        answer[i]=one
        this.assess4(one,j,i)
      }
      console.log(this.score)
      let obj=JSON.stringify({
        "userid":this.user.userid,
        "score":this.score,
        "chapid":this.chapid,
        "timecost":this.testTime,
      })
      console.log(this.testTime)
      this.request.post("/test/updateTest",obj).then(res => {
        if(res){
          this.$toast.add({severity:'success', summary: 'Success', detail:'提交成功', life: 3000});
        }else{
          this.$toast.add({severity:'error', summary: 'Error', detail:'提交失败，请重试', life: 3000});
        }
      })
      this.request.post("/test/getid",this.user).then(res => {
        console.log(res)
        let j=0
        for (let i=0;i<4;i++){
          for (let k=0;k<this.data[i].length;k++){
            let obj=JSON.stringify({
              "exerid":this.data[i][k].exerid,
              "testid":res+1,
              "userid":this.user.userid,
              "rightwrong":this.rightwrong[j],
              "chapid":this.chapid,
            })
            this.request.post("/test/updateTestSet",obj)
            j+=1
          }
        }
        console.log(res)
      })
      this.$router.push('/testDetail')
    },
    assess1(myAns,index,i){
      let stdAns = this.data[0][index].exerans//正确答案
      if (myAns===stdAns){
        this.rightwrong[i]='Y'
        this.score+=2
      }
      else {this.rightwrong[i]='N'}
    },
    assess2(myAns,index,i){
      console.log(this.data)
      let stdAns = this.data[1][index].answer//正确答案
      console.log(stdAns)
      let flag=true
      myAns.forEach((item) => {
        if (!(stdAns.includes(item))){
          flag=false
        }
      })
      stdAns.forEach((item) => {
        if (!(myAns.includes(item))){
          flag=false
        }
      })
      if (flag){
        this.rightwrong[i]='Y'
        this.score+=2
      }else {
        this.rightwrong[i]='N'
      }
    },
    assess3(myAns,index,i){
      let stdAns = this.data[2][index].exerans//正确答案
      if (myAns===stdAns){
        this.rightwrong[i]='Y'
        this.score+=2
      }
      else {this.rightwrong[i]='N'}
    },
    assess4(myAns,index,i){
      let stdAns = this.data[3][index].exerans//正确答案
      let cha=""
      if (myAns==="正确")cha="T"
      else if (myAns==="错误")cha="F"
      if (cha===stdAns){
        this.rightwrong[i]='Y'
        this.score+=2
      }
      else {this.rightwrong[i]='N'}
    },
  }
}
</script>
<style lang="scss" scoped>
</style>