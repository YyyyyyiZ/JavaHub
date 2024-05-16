<template>
  <Toast position="top-center"/>
    <div class="surface-0 flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden">
        <div class="grid justify-content-center p-2 lg:p-0" style="min-width:80%">
            <div class="col-12 xl:col-6" style="border-radius:56px; padding:0.3rem; background: linear-gradient(180deg, var(--primary-color), rgba(33, 150, 243, 0) 30%);">
                <div class="h-full w-full m-0 py-7 px-4" style="border-radius:53px; background: linear-gradient(180deg, var(--surface-50) 38.9%, var(--surface-0));">
                    <div class="text-center mb-5">
                        <img src="layout/images/logo-dark.svg" alt="Image" height="50" class="mb-3">
                        <div class="text-900 text-3xl font-medium mb-3">Welcome to Java Learning!</div>
                        <span class="text-600 font-medium">Sign in to continue</span>
                    </div>
                    <div class="w-full md:w-10 mx-auto">
                        <label for="userID" class="block text-900 text-xl font-medium mb-2">UserID</label>
                        <InputText id="userID" :rules="rules" v-model="user.userid" prop="userid" type="text" class="w-full mb-3" placeholder="UserID" style="padding:1rem;" />
                        <label for="password" class="block text-900 font-medium text-xl mb-2">Password</label>
                        <Password id="password" v-model="user.password" prop="password" placeholder="Password" :toggleMask="true" class="w-full mb-3" inputClass="w-full" inputStyle="padding:1rem"></Password>
                        <div class="flex align-items-center justify-content-between mb-5">
                            <a @click="signUp" class="font-medium no-underline ml-2 text-left cursor-pointer" style="color: var(--primary-color)">Sign up?</a>
                            <a @click="pswd" class="font-medium no-underline ml-2 text-right cursor-pointer" style="color: var(--primary-color)">Forgot password?</a>
                        </div>
                        <Button label="Sign In" class="w-full p-3 text-xl" @click="login"></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>


export default {
  name:'Login',
    data() {
      return {
        user:{},
        //表单校验规则没有生效，因为不是<form-item>
        rules:{
          userid:[
            {required:true,message:'请输入学号',trigger:'blur'}
          ],
          password:[
            {required:true,message:'请输入密码',trigger:'blur'}
          ],
        }
      }
    },
  computed: {
    logoColor() {
        if (this.$appState.darkTheme) return 'white';
        return 'dark';
    }
  },
  methods:{
    login(){
      //this.request()默认为get方法
      if(this.user.userid=="admin"){
        this.request.post("/userl/logadmin").then(res=>{
          localStorage.setItem("user",res)
        })
        if(this.user.password=="admin"){
          this.$router.push("/admin")
        }else{
          alert("登录错误");
        }
      }
      else{
        this.request.post("/user/login",this.user).then(res => {
          if(res.code === "200"){
            localStorage.setItem("user",JSON.stringify(res.data))
            //判断当前是处于学习界面还是习题界面
            localStorage.setItem("learn","0")
            localStorage.setItem("exercise","0")
            this.$toast.add({severity:'success', summary: 'Success', detail:'登录成功', life: 3000});
            this.$router.push("/")
          } else{
            this.$toast.add({severity:'error', summary: 'Error', detail:'用户名或密码错误', life: 3000});
          }
        })
      }
    },
    signUp(){
      this.$router.push("/signUp")
    },
    pswd(){
      this.$router.push("/pswd")
    },
  },
}
</script>

<style scoped>

</style>