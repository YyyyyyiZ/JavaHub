<template>
  <div>
    <Toast />
    <Toast position="top-left" group="tl" />
    <div class="card">
      <h4>填空题</h4>
      <component :is="SingleCh" :data="data" ref="fill" @answer="submit2"></component>
    </div>
    <Toolbar class="mb-4">
      <template v-slot:start>
        <div class="my-1">
          <Button class="p-button-text p-button-success" disabled><Timer class="timer" ref="timer" @time="getTime"></Timer></Button>
        </div>
      </template>
      <template v-slot:end>
        <div class="my-1">
          <Button label="上一题" :disabled="dis1" icon="pi pi-arrow-left" class="p-button mr-3" @click="before" id="before"/>
          <Button label="下一题" :disabled="dis2" icon="pi pi-arrow-right" class="p-button mr-3" @click="next" id="next"/>
          <Button label="提交" icon="pi pi-arrow-up" class="p-button-info mr-3" @click="submit"/>
          <Button label="收藏" icon="pi pi-star" class="p-button-warning mr-3" @click="collect"/>
        </div>
      </template>
    </Toolbar>
    <div class="card" :hidden="hidden">
      <p id="assess"></p>
      <p id="ans"></p>
      <p id="kp"></p>
    </div>
    <div class="card" :hidden="hidden">
      <h4>评论</h4>
      <div class="flex sm-3">
        <div class="p-inputgroup">
          <InputText placeholder="发表评论······" v-model="myComment"/>
          <Button icon="pi pi-comment" class="p-button-warning" @click="newComment"/>
        </div>
      </div>
      <divider></divider>
      <div v-for="comment in comments" :key="comment.userid" class="border-round border-1 surface-border p-4">
        <div class="flex mb-3">
          <Chip :label="comment.userid + comment.username" icon="pi pi-user" class="mb-1"></Chip>
        </div>
        <p>{{ comment.comment }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import Timer from "@/components/Timer";
import {  defineAsyncComponent } from 'vue'
import router from "@/router";

export default {
  components:{
    'Timer':Timer,
  },
  data() {
    return {
      myComment:null,
      exerTime:0,
      all:null,
      data:null,
      length:0,
      dis1:false,
      dis2:false,
      SingleCh:null,
      comments:null,
      exerid:null,
      rightwrong:null,
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      secid:localStorage.getItem("user")?JSON.parse(localStorage.getItem("secid")):{},
      hidden:true,
      products: null,
      responsiveOptions: [
        {
          breakpoint: '1024px',
          numVisible: 3,
          numScroll: 3
        },
        {
          breakpoint: '600px',
          numVisible: 2,
          numScroll: 2
        },
        {
          breakpoint: '480px',
          numVisible: 1,
          numScroll: 1
        }
      ]
    }
  },
  productService: null,
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
  },
  mounted() {
    let obj=JSON.stringify({
      "exertype":"填空题",
      "secid":this.secid,
    })
    this.request.post("/exercise/type",obj).then(res => {
      console.log(res)
      this.all=res
      this.data=this.all[length]
      this.comments=this.data.comments
      this.exerid=this.data.exerid
      document.getElementById("ans").innerText="参考答案："+this.data.exerans
      document.getElementById("kp").innerText="知识点："+this.data.knowpoint
      this.checkLast()
      this.SingleCh=defineAsyncComponent(() => import("@/pattern/Fill"))
    })
  },
  methods:{
    newComment(){
      console.log(this.myComment)
      let obj=JSON.stringify({
        "userid":this.user.userid,
        "comment":this.myComment,
        "exerid":this.exerid,
      })
      this.request.post("/exercise/comment",obj).then(res => {
        if(res){
          this.$toast.add({severity:'success', summary: 'Success', detail:'发表成功', life: 3000});
        } else{
          this.$toast.add({severity:'error', summary: 'Error', detail:'发表失败', life: 3000});
        }
      })
      this.comments.push({
        "userid":this.user.userid,
        "username":this.user.username,
        "comment":this.myComment
      })
      this.myComment=null
    },
    checkLast(){
      console.log(this.length)
      if (this.length===this.all.length-1){
        this.dis2=true
        this.dis1=false
      } else if(this.length===0){
        this.dis1=true
        this.dis2=false
      } else {
        this.dis1=false
        this.dis2=false
      }
    },
    reload () {
      this.data=this.all[this.length]
      this.comments=this.data.comments
      this.exerid=this.data.exerid
      document.getElementById("ans").innerText="参考答案："+this.data.exerans
      document.getElementById("kp").innerText="知识点："+this.data.knowpoint
      this.checkLast()
    },
    getTime(time){
      this.exerTime=time
    },
    submit(){
      this.$refs.timer.reload()
      this.$refs.fill.getMsg()
      this.hidden=false;
    },
    submit2(answer){
      let ans = this.data.exerans
      console.log(answer)
      console.log(ans)
      if (answer===ans){
        this.rightwrong='Y'
        document.getElementById("assess").innerHTML="回答正确"
      }else {
        this.rightwrong='N'
        document.getElementById("assess").innerHTML="回答错误"
      }
      console.log(this.rightwrong)
      let obj=JSON.stringify({
        "userid":this.user.userid,
        "exerid":this.exerid,
        "rightwrong":this.rightwrong,
        "timecost":this.exerTime
      })
      this.request.post("/exerrecord/insert",obj)
    },
    next(){
      this.hidden=true;
      this.length=this.length+1
      this.reload();
    },
    before(){
      this.hidden=true;
      this.length=this.length-1
      this.reload();
    },
    collect(){
      let obj=JSON.stringify({
        "userid":this.user.userid,
        "exerid":this.exerid,
      })
      console.log(obj)
      this.request.post("/collecset/collect",obj).then(res => {
        if (res){
          this.$toast.add({severity:'success', summary: '收藏成功', life: 1000});
        } else {
          this.$toast.add({severity:'error', summary: 'Error', detail:'此题已收藏', life: 3000});
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>
.product-item {
  .product-item-content {
    border: 1px solid var(--surface-border);
    border-radius: 3px;
    margin: .3rem;
    text-align: center;
    padding: 2rem 0;
  }

  .product-image {
    width: 50%;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23)
  }
}
</style>