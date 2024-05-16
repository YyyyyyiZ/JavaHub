<template>
  <Toast position="top-center"/>
  <div class="surface-0 flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
    <div class="grid justify-content-center p-2 lg:p-0" style="min-width:80%">
      <div class="col-12 xl:col-6" style="border-radius:56px; padding:0.3rem; background: linear-gradient(180deg, rgba(33, 150, 243, 0.4) 10%, rgba(33, 150, 243, 0) 30%);">
        <div class="h-full w-full m-0 py-7 px-4" style="border-radius:53px; background: linear-gradient(180deg, var(--surface-50) 38.9%, var(--surface-0));">
          <div class="text-center mb-5">
            <img src="layout/images/logo-blue.svg" alt="Image" height="50" class="mb-3">
            <div class="text-900 text-3xl font-medium mb-3">Retrieve Password</div>
          </div>
          <div class="w-full md:w-10 mx-auto">
            <label class="block text-900 text-md font-small mb-2">StudentID</label>
            <div class="col-12 md:col-12">
              <div class="p-inputgroup">
                <InputText placeholder="Price" v-model="user.userid"/>
                <span class="p-inputgroup-addon">
                  <RadioButton name="rb1" value="rb1" v-model="radioValue1" :checked="checked" @click="findVerifyques"/>
                </span>
              </div>
            </div>
            <label for="verifyans" class="block text-900 text-md font-small mb-2">{{ user.verifyques }}</label>
            <div class="col-12 md:col-12">
              <div class="p-inputgroup">
                <InputText placeholder="请输入回答" v-model="answer" class="mb-4"/>
              </div>
            </div>
            <Button label="Submit" class="w-full p-3 text-xl p-button-info" color="rgba(33, 150, 243, 0.4)" @click="reset"></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name:'Pswd',
  data() {
    return {
      user:{},
      checked: '',
      verifyans:'',
      answer:'',
    }
  },
  created() {
    this.checked = false
  },
  computed: {
    logoColor() {
      if (this.$appState.darkTheme) return 'white';
      return 'dark';
    }
  },
  methods:{
    findVerifyques(){
      this.request.post("/user/pswd",this.user).then(res => {
        if(res.code === "200"){
          this.user.verifyques = res.data.verifyques
          this.checked = false
          this.verifyans = res.data.verifyans
          console.log(this.user.verifyques)
          console.log(this.verifyans)
        } else{
          this.$toast.add({severity:'error', summary: 'Error', detail:'学号错误', life: 3000});
          this.checked = false
        }
      })
    },
    reset(){
      console.log('reset')

      if(this.answer === this.verifyans){
        this.request.post("/user/reset",this.user).then(res => {
          if(res){
            this.$toast.add({severity:'success', summary: '重置密码成功', detail:'新密码为123456', life: 3000});
            //this.$router.push("/login")
          }
          else {
            this.$toast.add({severity:'error', summary: '系统错误', detail:'请稍后重试', life: 3000});
          }
        })
      } else{
        this.$toast.add({severity:'error', summary: 'Error', detail:'回答错误', life: 3000});
      }
    }
  }
}
</script>

<style scoped>
.pi-eye {
  transform:scale(1.6);
  margin-right: 1rem;
}

.pi-eye-slash {
  transform:scale(1.6);
  margin-right: 1rem;
}
</style>