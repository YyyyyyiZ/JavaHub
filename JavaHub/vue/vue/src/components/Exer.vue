<template>
  <div>
    <Toolbar class="mb-4">
      <template v-slot:start>
        <div class="my-2">
          <h4>{{section}}</h4>
        </div>
      </template>
      <template v-slot:end>
      </template>
    </Toolbar>
    <div class="card">
      <div class="grid">
        <div class="col-9">
          <h5>单选题</h5>
          <ProgressBar :value="value1"></ProgressBar>
        </div>
        <div class="col-3">
          <Button label="开始练习" icon="pi pi-send" class="p-button mr-3" @click="openSingleCh"/>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="grid">
        <div class="col-9">
          <h5>多选题</h5>
          <ProgressBar :value="value2"></ProgressBar>
        </div>
        <div class="col-3">
          <Button label="开始练习" icon="pi pi-send" class="p-button mr-3" @click="openMultiCh"/>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="grid">
        <div class="col-9">
          <h5>判断题</h5>
          <ProgressBar :value="value3"></ProgressBar>
        </div>
        <div class="col-3">
          <Button label="开始练习" icon="pi pi-send" class="p-button mr-3" @click="openJudge"/>
        </div>
      </div>
    </div>
    <div class="card">
      <div class="grid">
        <div class="col-9">
          <h5>填空题</h5>
          <ProgressBar :value="value4"></ProgressBar>
        </div>
        <div class="col-3">
          <Button label="开始练习" icon="pi pi-send" class="p-button mr-3" @click="openFill"/>
        </div>
      </div>
    </div>
  </div>
  <Dialog header="新增习题" v-model:visible="displayBasic" :breakpoints="{'960px': '50vw', '640px': '75vw'}"
          :style="{width: '40vw'}">
    <TabView ref="tabview1">
      <TabPanel header="单选题">
        <p>题目</p>
        <Textarea v-model="SingleQues" rows="3" cols="58"/>
        <Divider/>
        <p>选项</p>
        <div class="grid p-fluid">
          <div class="field col-12 md:col-6">
            <div class="p-inputgroup">
              <span class="p-inputgroup-addon"><Checkbox v-model="SingleChecked1" :binary="true"/></span>
              <InputText placeholder="选项A"/>
            </div>
          </div>
          <div class="field col-12 md:col-6">
            <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="SingleChecked2" :binary="true"/>
                    </span>
              <InputText placeholder="选项B"/>
            </div>
          </div>
          <div class="field col-12 md:col-6">
            <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="SingleChecked3" :binary="true"/>
                    </span>
              <InputText placeholder="选项C"/>
            </div>
          </div>
          <div class="field col-12 md:col-6">
            <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="SingleChecked4" :binary="true"/>
                    </span>
              <InputText placeholder="选项D"/>
            </div>
          </div>
        </div>
      </TabPanel>

      <TabPanel header="多选题">
        <p>题目</p>
        <Textarea v-model="MultiQues" rows="3" cols="58"/>
        <Divider/>
        <p>选项</p>
        <ScrollPanel style="width: 100%; height: 200px" class="custombar1">
          <div class="grid p-fluid">
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked1" :binary="true"/>
                    </span>
                <InputText placeholder="选项A"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked2" :binary="true"/>
                    </span>
                <InputText placeholder="选项B"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked3" :binary="true"/>
                    </span>
                <InputText placeholder="选项C"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked4" :binary="true"/>
                    </span>
                <InputText placeholder="选项D"/>
              </div>
            </div>
          </div>
          <div class="grid p-fluid">
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked5" :binary="true"/>
                    </span>
                <InputText placeholder="选项E" />
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked6" :binary="true"/>
                    </span>
                <InputText placeholder="选项F"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked7" :binary="true"/>
                    </span>
                <InputText placeholder="选项G"/>
              </div>
            </div>
            <div class="field col-12 md:col-6">
              <div class="p-inputgroup">
                    <span class="p-inputgroup-addon">
                        <Checkbox v-model="checked8" :binary="true"/>
                    </span>
                <InputText placeholder="选项H"/>
              </div>
            </div>
          </div>
        </ScrollPanel>
      </TabPanel>
      <TabPanel header="判断题">
        <p>题目</p>
        <Textarea v-model="JudgeQues" rows="5" cols="58"/>
        <Divider/>
        <p>答案</p>
        <div class="field-radiobutton">
          <RadioButton inputId="city1" name="city" value="Chicago" v-model="T"/>
          <label for="city1">正确</label>
        </div>
        <div class="field-radiobutton">
          <RadioButton inputId="city2" name="city" value="Los Angeles" v-model="T"/>
          <label for="city2">错误</label>
        </div>
      </TabPanel>
      <TabPanel header="填空题">
        <p>题目</p>
        <Textarea v-model="FillQues" rows="3" cols="58"/>
        <Divider/>
        <p>答案</p>
        <Textarea v-model="FillAns" rows="1" cols="58"/>
      </TabPanel>
    </TabView>
    <template #footer>
      <Button label="取消" icon="pi pi-times" @click="closeBasic" class="p-button-text"/>
      <Button label="新增" icon="pi pi-check" @click="addExer" autofocus/>
    </template>
  </Dialog>
</template>

<script>
import {ref} from 'vue';
import router from "@/router";

export default {
  data() {
    return {
      self:this,
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      secid:"1.1",
      section:"1.1Java语言概述",
      displayBasic: false,
      value1: null,
      value2: null,
      value3: null,
      value4: null,
      SingleQues:"",
      MultiQues:"",
      JudgeQues:"",
      FillQues:"",
      FillAns:"",
      products: null,
      productDialog: false,
      product: {},
      submitted: false,
      cities: [
        {name: '单选题', code: 'SingleCh'},
        {name: '多选题', code: 'MultiCh'},
        {name: '判断题', code: 'Judge'},
        {name: '填空题', code: 'Fill'},
      ],
    }
  },
  setup() {
    const SingleChecked1 = ref(false);
    const SingleChecked2 = ref(false);
    const SingleChecked3 = ref('');
    const SingleChecked4 = ref('');
    const checked1 = ref(false);
    const checked2 = ref(false);
    const checked3 = ref('');
    const checked4 = ref('');
    const checked5 = ref(false);
    const checked6 = ref(false);
    const checked7 = ref('');
    const checked8 = ref('');
    const T = ref('');
    return {SingleChecked1, SingleChecked2, SingleChecked3, SingleChecked4, checked1, checked2, checked3, checked4,checked5, checked6, checked7, checked8, T}
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
    this.self=this
  },
  mounted() {
    let obj=JSON.stringify({
      "secid":this.secid,
      "userid":this.user.userid
    })
    localStorage.setItem("secid",this.secid)
    this.request.post("/exercise/section",obj).then(res => {
      console.log(res)
      this.value1=res[1]*100/res[0]
      this.value2=res[3]*100/res[2]
      this.value3=res[5]*100/res[4]
      this.value4=res[7]*100/res[6]
    })

    let _this=this;
    window.addEventListener("setItemEvent",function(e){
      if(e.key==="section"){
        _this.section=e.newValue;
        let reg = /\d+\.\d+/
        _this.secid = e.newValue.match(reg)[0]
        localStorage.setItem("secid",_this.secid)
        let obj=JSON.stringify({
          "secid":_this.secid,
          "userid":_this.user.userid
        })
        _this.request.post("/exercise/section",obj).then(res => {
          console.log(res)
          _this.value1=res[1]*100/res[0]
          _this.value2=res[3]*100/res[2]
          _this.value3=res[5]*100/res[4]
          _this.value4=res[7]*100/res[6]
        })

      }
    })
  },
  methods: {
    closeBasic() {
      this.displayBasic = false;
    },
    addExer(){
    },
    openSingleCh(){
      this.$router.push('/single')
      localStorage.setItem("type","单选题")
    },
    openMultiCh(){
      this.$router.push('/multi')
      localStorage.setItem("type","多选题")
    },
    openJudge(){
      this.$router.push('/judge')
      localStorage.setItem("type","判断题")
    },
    openFill(){
      this.$router.push('/fill')
      localStorage.setItem("type","填空题")
    },
  }
}
</script>

<style lang="scss" scoped>
.table-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.col-3 {
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: flex-end;

}

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