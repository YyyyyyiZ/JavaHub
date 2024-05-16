<template>
  <Toast position="top-center"/>
  <div class="grid p-fluid">
    <div class="col-12 md:col-6">
      <div class="card">
        <form>
          <InputText class="col-12 md:col-6" id="userID" type="text" v-model="user.userid" disabled placeholder="学号"/>
          <br><br>
          <InputText class="col-12 md:col-6" id="userName" type="text" v-model="form.username" :disabled="flag" placeholder="用户名"/>
          <br><br>
          <InputText class="col-12 md:col-6" id="school" type="text" v-model="form.school" :disabled="flag" placeholder="学校"/>
          <br><br>
          <InputText class="col-12 md:col-6" id="grade" type="text" v-model="form.grade" :disabled="flag" placeholder="年级"/>
          <br><br>
          <InputText class="col-12 md:col-6" id="gender" type="text" v-model="form.gender" :disabled="flag" placeholder="性别"/>
          <br><br>
          <InputText class="col-12 md:col-6" id="verifyques" type="text" v-model="form.verifyques" :disabled="flag" placeholder="验证问题"/>
          <br><br>
          <InputText class="col-12 md:col-6" id="verifyans" type="text" v-model="form.verifyans" :disabled="flag" placeholder="验证答案"/>
          <br><br>
          <Password id="password" type="text" v-model="form.password" :disabled="flag" :feedback="false" toggleMask placeholder="密码"/>
          <br>
        </form>
        <Button :label="buttonLabel" class="w-full p-3 text-md" @click="edit"></button>
      </div>
    </div>
    <div class="col-12 md:col-6">
      <div class="card">
        <FullCalendar :options="calendarOptions" id="calendar"/>
      </div>
    </div>
  </div>
</template>
<script>
import '@fullcalendar/core/vdom'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import router from "@/router";


export default {
  name:'User',
  components: {
    FullCalendar,
  },
  data() {
    return {
      value: new Date(),
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      buttonLabel:"修改个人信息",
      flag:true,
      form:{},
      calendarOptions: {
        plugins: [dayGridPlugin, interactionPlugin],
        initialView: 'dayGridMonth',
        events: []
      },
    }
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
    this.request.get("/user").then(res => {
      console.log(res)
      this.form=res[1]
    })
  },
  mounted(){
    this.request.post("/user/calendar",this.user).then(res => {
      console.log(res)
      this.calendarOptions.events=res
    })
  },
  methods:{
    edit(){
      if(this.flag===true){
        this.flag=false
        this.buttonLabel="确认修改"
      }else{//确认修改，提交数据
        this.flag=true;
        this.buttonLabel="修改个人信息"
        this.request.post("/user",this.form).then(res => {
          if(res){
            this.$toast.add({severity:'success', summary: 'Success', detail:'修改信息成功', life: 3000});
          } else{
            this.$toast.add({severity:'error', summary: 'Error', detail:'修改信息失败', life: 3000});
          }
        })
      }
    },
  },
}
</script>

<style lang="scss" scoped>

.sizes {
  .p-inputtext {
    display: block;
    margin-bottom: .5rem;

    &:last-child {
      margin-bottom: 0;
    }
  }
}

.field * {
  display: block;
}

</style>