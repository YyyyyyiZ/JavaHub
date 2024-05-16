<template>
  <div>
    <p>{{ data.exerques }}</p>
    <InputText type="text" v-model="value1" placeholder="请填空"/>
  </div>
</template>

<script>
export default {
  props: {
    data: Object
  },
  data(){
    return{
      value1:"",
    }
  },
  name: "Fill",
  methods:{
    getMsg(){
      console.log("msg")
      this.$emit("answer",this.value1)
    }
  }
}
</script>

<style scoped>

</style>