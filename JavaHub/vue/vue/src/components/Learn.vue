<template>
  <Toast />
  <div class="grid p-fluid">
    <div class="col-12">
      <div class="card">
        <h4>{{section}}</h4>
        <Accordion :multiple="true">
          <AccordionTab v-for="tab in tabs" :key="tab.knowid" :header="tab.knowpoint" >
            <p>{{tab.knowdetail}}</p>
          </AccordionTab>
        </Accordion>
      </div>
    </div>
    <div class="col-12">
      <div class="card">
        <h5>我的笔记</h5>
        <Editor @text-change="textChange" v-model="value1" editorStyle="height: 320px" ref="RichText"/>
        <divider></divider>
        <el-button type="primary" @click="saveNote">保存笔记</el-button>
      </div>
    </div>
    <div class="col-12">
      <div class="card">
        <h5>我的文件</h5>
        <Galleria :value="images" :responsiveOptions="galleriaResponsiveOptions" :numVisible="7" :circular="true" containerStyle="max-width: 800px; margin: auto">
          <template #item="slotProps">
            <div>
              <img ref="now" :src="slotProps.item.url" :alt="slotProps.item.filename" style="width:100%;display: block; height: 60vh" />
            </div>
          </template>
          <template #thumbnail="slotProps">
            <img :src="slotProps.item.url" :alt="slotProps.item.filename" style="width: 100%; display: block;" />
          </template>
        </Galleria>
        <divider></divider>
        <el-upload
            ref="uploadRef"
            class="upload-demo"
            action="/api/file/upload"
            :auto-upload="true"
            :data="param"
            :multiple="true"
            :on-success="uploaded"
        >
          <template #trigger>
            <el-button type="success">上传图片</el-button>
          </template>
          <template #tip>
            <div class="el-upload__tip">
              jpg/png files with a size less than 500kb
            </div>
          </template>
        </el-upload>
      </div>
    </div>
  </div>
</template>

<script>

import router from "@/router";

export default {
  data() {
    return {
      value1: "<p>hello world</p>",
      items:[{
        label:'删除图片',
        icon:'pi pi-times'
      }],
      browseTime:0,
      clearTimeSet:null,
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      secid:"1.1",
      section:"1.1Java语言概述",
      userid:JSON.parse(localStorage.getItem("user")).userid,
      tabs:"",
      uploadedFiles: [],
      files: [],
      totalSize: 0,
      totalSizePercent: 0,
      products: [],
      images: [],
      param:{
        "userid":null,
        "secid":null,
      },
      galleriaResponsiveOptions: [{breakpoint: "1024px", numVisible: 5,},
        {breakpoint: "960px", numVisible: 4,},
        {breakpoint: "768px", numVisible: 3,},
        {breakpoint: "560px", numVisible: 1,},],
      carouselResponsiveOptions: [{breakpoint: "1024px",numVisible: 3, numScroll: 3,},
        {breakpoint: "768px", numVisible: 2, numScroll: 2,},
        {breakpoint: "560px", numVisible: 1, numScroll: 1,},],
    };
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
    this.param={
      "userid":this.userid,
      "secid":this.secid
    }
    this.setTime()
    let origin=JSON.stringify({
      "secid":"1.1",
    })
    this.request.post("/knowledge/section",origin).then(res => {
      this.tabs=res
    })
    let obj=JSON.stringify({
      "userid": this.user.userid,
      "secid": this.secid,
    })
    this.request.post("/file/section",obj).then(res => {
      this.images = res.data
      console.log(res.data)
    })
    this.request.post("/note/readNote",this.param).then(res => {
      this.value1=res
      console.log(res)
    })
  },
  mounted() {
    let _this=this;
    //不知道如何remove
    window.addEventListener("setItemEvent",function(e){
      if(e.key==="section"){
        let oldSection = _this.section
        let newSection=e.newValue;
        _this.reload(oldSection,newSection)
      }
    })
  },
  beforeUnmount() {
    console.log(this.browseTime/60)
    let obj=JSON.stringify({
      "userid":this.user.userid,
      "timelast":this.browseTime/60,
      "secid":this.secid,
    })
    this.request.post("/learnrecord/update",obj)
    clearInterval(this.clearTimeSet); // 离开页面后清除定时器
    //window.removeEventListener()
  },
  methods: {
    saveNote(){
      console.log(typeof this.value1)
      let obj=JSON.stringify({
        "userid":this.user.userid,
        "remark":this.value1,
        "secid":this.secid,
      })
      this.request.post("/note/updateNote",obj)
    },
    reloadImg(){
      let obj2=JSON.stringify({
        "userid": this.user.userid,
        "secid": this.secid,
      })
      this.request.post("/file/section",obj2).then(res => {
        this.images = res.data
        console.log(res.data)
      })
    },
    uploaded(){
      this.reloadImg()
      this.$toast.add({severity:'success', summary: 'Success', detail:'上传图片成功', life: 3000});
    },
    reload(oldSection,newSection){
      this.section=newSection
      let reg = /\d+\.\d+/
      let newSecid = newSection.match(reg)[0]
      this.secid = newSecid
      this.param={
        "userid":this.userid,
        "secid":newSecid
      }
      let oldSecid=oldSection.match(reg)[0]
      let obj2=JSON.stringify({
        "userid":this.user.userid,
        "timelast":this.browseTime/60,
        "secid":oldSecid,
      })
      this.request.post("/learnrecord/update",obj2)
      console.log(this.browseTime/60)
      let obj=JSON.stringify({
        "secid":newSecid,
      })
      this.request.post("/knowledge/section",obj).then(res => {
        this.tabs=res
      })

      let image=JSON.stringify({
        "userid": this.user.userid,
        "secid": newSecid,
      })
      this.request.post("/file/section",image).then(res => {
        this.images = res.data
      })
      this.request.post("/note/readNote",image).then(res => {
        this.value1=res
        console.log(res)
      })
      this.browseTime=0;
    },
    setTime() {
      this.clearTimeSet = setInterval(() => {
        this.browseTime++;
      }, 1000);
    },
  }
};

</script>

<style lang="scss" scoped>
@import '../assets/demo/badges.scss';
@import '../assets/demo/items.scss';
::v-deep(.custom-progress-bar) {
  .p-progressbar-value {
    background-color: #f44336;
  }
}</style>

