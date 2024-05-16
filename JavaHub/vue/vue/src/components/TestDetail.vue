<template>
  <Toast position="top-center"/>
  <div>
    <Toolbar class="mb-4">
      <template v-slot:start>
        <div class="my-2">
          <Button :label="label" class="p-button-raised p-button-info p-button-text" disabled/>
        </div>
      </template>
      <template v-slot:end>
        <div class="my-2">
          <Button label="返回" icon="pi pi-refresh" class="p-button mr-3" @click="goBack"/>
        </div>
      </template>
    </Toolbar>

    <div class="card">
      <DataTable :value="test">
        <Column field="name" header="编号" hidden="true" style="max-width:0px">
          <template #body="slotProps">
            {{slotProps.data.testid}}
          </template>
        </Column>
        <Column field="size" header="日期">
          <template #body="slotProps">
            {{slotProps.data.testdate}}
          </template>
        </Column>
        <Column field="size" header="得分/100">
          <template #body="slotProps">
            {{slotProps.data.score}}
          </template>
        </Column>
        <Column field="type" header="用时/分钟">
          <template #body="slotProps">
            {{slotProps.data.timecost}}
          </template>
        </Column>
        <Column header="查看">
          <template #body="slotProps">
            <Button type="button" icon="pi pi-search" class="p-button-success" :id=slotProps.data.testid @click="openDialog($event)" />
          </template>
        </Column>
      </DataTable>
    </div>

    <Dialog header="试卷详情" v-model:visible="dialogVisible" :style="{width: '75vw'}" :maximizable="true" :modal="true" :contentStyle="{height: '300px'}">
      <DataTable :value="customers1" :scrollable="true" scrollHeight="flex" v-model:expandedRows="expandedRows">
        <Column field="expander" style="min-width:0px" :expander="true"></Column>
        <Column field="exerid" style="max-width:0px" hidden="true">
          <template #body="slotProps">
            {{slotProps.data.exerid}}
          </template>
        </Column>
        <Column field="exerques" header="题目" style="min-width:400px">
          <template #body="slotProps">
            {{slotProps.data.exerques}}
          </template>
        </Column>
        <Column field="exertype" header="题型" style="min-width:150px">
          <template #body="slotProps">
            {{slotProps.data.exertype}}
          </template>
        </Column>
        <Column field="knowpoint" header="知识点" style="min-width:200px">
          <template #body="slotProps">
            {{slotProps.data.knowpoint}}
          </template>
        </Column>
        <Column field="verified" header="正误" dataType="boolean" bodyClass="text-center" style="min-width:50px">
          <template #body="{data}">
            <Button :icon="{'pi pi-check':data.rightwrong=='Y','pi pi-times':data.rightwrong=='N'}" :class="{'p-button-rounded p-button-success p-button-text':data.rightwrong=='Y','p-button-rounded p-button-danger p-button-text':data.rightwrong=='N'}" disabled></Button>
          </template>
        </Column>
        <Column field="verified" header="收藏" dataType="boolean" bodyClass="text-center" style="min-width:50px">
          <template #body="{data}">
            <Button :id="data.exerid" @click="changeCollec($event,data)" :icon="{'pi pi-star-fill':data.collected=='Y','pi pi-star':data.collected=='N'}"
                    :class="{'p-button-rounded p-button-warning p-button-text':data.collected=='Y','p-button-rounded p-button-secondary p-button-text':data.collected=='N'}"></Button>
          </template>
        </Column>

        <template #expansion="slotProps">
          <div v-if="slotProps.data.exertype==='单选题'">
            <SingleCh :data="slotProps.data"></SingleCh>
            <p>参考答案：{{slotProps.data.exerans}}</p>
          </div>
          <div v-if="slotProps.data.exertype==='多选题'">
            <MultiCh :data="slotProps.data"></MultiCh>
            <p>参考答案：{{slotProps.data.answer}}</p>
          </div>
          <div v-if="slotProps.data.exertype==='判断题'">
            <Judge :data="slotProps.data"></Judge>
            <p>参考答案：{{slotProps.data.exerans}}</p>
          </div>
          <div v-if="slotProps.data.exertype==='填空题'">
            <Fill :data="slotProps.data"></Fill>
            <p>参考答案：{{slotProps.data.exerans}}</p>
          </div>
        </template>

      </DataTable>
      <template #footer>
        <Button label="Ok" icon="pi pi-check" @click="closeDialog" />
      </template>
    </Dialog>

  </div>
</template>

<script>
import SingleCh from "@/pattern/SingleCh";
import MultiCh from "@/pattern/MultiCh";
import Judge from "@/pattern/Judge";
import Fill from "@/pattern/Fill";
import router from "@/router";

export default {
  data() {
    return {
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      chapid:localStorage.getItem("chapid")?localStorage.getItem("chapid"):"",
      chapname:localStorage.getItem("chapname")?localStorage.getItem("chapname"):"",
      expandedRows: [],
      test: {},
      dialogVisible: false,
      testid:"",
      customers1:null
    }
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
    this.label="第"+this.chapid+"章"+" "+this.chapname;
  },
  mounted() {
    let obj=JSON.stringify({
      "userid":this.user.userid,
      "chapid":this.chapid
    })
    console.log(obj)
    this.request.post("/test/selectAll",obj).then(res => {
      this.test=res
    })
  },
  methods: {
    openDialog(e) {
      this.testid=e.currentTarget.id;
      let test=JSON.stringify({
        "userid":this.user.userid,
        "testid":this.testid
      })
      this.request.post("/test/selectOne",test).then(res => {
        this.customers1=res
        console.log(res)
      })
      this.dialogVisible = true;
    },
    closeDialog() {
      this.dialogVisible = false;
    },
    goBack(){
      this.$router.push('/test')
    },
    changeCollec(e,data){
      if(e.currentTarget.getAttribute('class')==='p-button p-component p-button-icon-only p-button-rounded p-button-warning p-button-text'){
        console.log('已收藏，点击后取消收藏')
        data.collected='N'
        let obj = JSON.stringify({
          "exerid":data.exerid,
          "testid":data.testid,
          "collected":"N"
        })
        this.request.post("/collecset/change",obj).then(res => {
          if(res){
            this.$toast.add({severity:'success', summary: 'Success', detail:'取消收藏成功', life: 3000});
          }else{
            this.$toast.add({severity:'error', summary: 'Error', detail:'取消收藏失败', life: 3000});
          }
        })
      }
      else{
        console.log('未收藏，点击后收藏')
        data.collected='Y'
        let obj = JSON.stringify({
          "exerid":data.exerid,
          "testid":data.testid,
          "collected":"Y"
        })
        this.request.post("/collecset/change",obj).then(res => {
          if(res){
            this.$toast.add({severity:'success', summary: 'Success', detail:'收藏成功', life: 3000});
          }else{
            this.$toast.add({severity:'error', summary: 'Error', detail:'收藏失败', life: 3000});
          }
        })
      }
    }
  },
  components:{
    'SingleCh':SingleCh,
    'MultiCh':MultiCh,
    'Judge':Judge,
    'Fill':Fill,
  },
}
</script>

<style scoped lang="scss">
.p-filter-column {
  .p-multiselect, .p-dropdown, .p-inputtext {
    width: 100%;
  }
}
::v-deep(.p-datatable-frozen-tbody) {
  font-weight: bold;
}

::v-deep(.p-datatable-scrollable .p-frozen-column) {
  font-weight: bold;}
</style>