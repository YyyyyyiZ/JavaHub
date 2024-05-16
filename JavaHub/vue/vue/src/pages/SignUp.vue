<template>
  <Toast position="top-center"/>
  <div class="surface-0 flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
    <div class="grid justify-content-center p-2 lg:p-0" style="min-width:80%">
      <div class="col-12 xl:col-6" style="border-radius:56px; padding:0.3rem; background: linear-gradient(180deg, rgba(233, 30, 99, 0.4) 10%, rgba(33, 150, 243, 0) 30%);">
        <div class="h-full w-full m-0 py-7 px-4" style="border-radius:53px; background: linear-gradient(180deg, var(--surface-50) 38.9%, var(--surface-0));">
          <div class="text-center mb-5">
            <img src="layout/images/logo-error.svg" alt="Image" height="50" class="mb-1">
            <div class="text-900 text-3xl font-medium mb-1">Sign up and join us!</div>
          </div>

          <div class="w-full md:w-10 mx-auto">
            <label for="userid" class="block text-900 text-md font-small mb-2">StudentID</label>
            <InputText id="userID" v-model="form.userid" type="text" class="w-full mb-2" placeholder="UserID" style="padding:1rem;" />

            <label for="password1" class="block text-900 font-small text-md mb-2">Password</label>
            <Password id="password1" v-model="form.password" placeholder="Password" :toggleMask="true" class="w-full mb-2" inputClass="w-full" inputStyle="padding:1rem"></Password>

            <label for="password2" class="block text-900 font-small text-md mb-2">Confirm Password</label>
            <Password id="password2" v-model="form.password2" placeholder="Confirm Password" :toggleMask="true" class="w-full mb-2" inputClass="w-full" inputStyle="padding:1rem"></Password>

            <label for="verifyques" class="block text-900 text-md font-small mb-2">Set a verification question to retrieve password</label>
            <InputText id="verifyQues" v-model="form.verifyques" type="text" class="w-full mb-2" placeholder="Verification Question" style="padding:1rem;" />

            <label for="verifyans" class="block text-900 text-md font-small mb-2">Answer to your verification question</label>
            <InputText id="verifyAns" v-model="form.verifyans" type="text" class="w-full mb-2" placeholder="Verification Answer" style="padding:1rem;" />
            <Button label="Sign Up" class="w-full p-3 text-xl p-button-danger" @click="signUp"></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name:'SignUp',
  data() {
    return {
      form:{},
    }
  },
  computed: {
    logoColor() {
      if (this.$appState.darkTheme) return 'white';
      return 'dark';
    }
  },
  methods:{
    signUp(){
      if(this.form.password !== this.form.password2){
        this.$toast.add({severity:'error', summary: 'Error', detail:'两次密码不一致', life: 3000});
        return false
      }
      this.request.post("/user/register",this.form).then(res => {
        if(res.code === "200"){
          this.$toast.add({severity:'success', summary: 'Success', detail:'注册成功', life: 3000});
          this.$router.push("/login")
        } else{
          this.$toast.add({severity:'error', summary: 'Error', detail:'注册失败', life: 3000});
        }
      })
    }
  }
}
</script>

<style scoped>
</style>