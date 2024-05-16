<template>
  <div>
    <p>{{data.exerques}}</p>
    <div class="field-radiobutton">
      <Checkbox inputId="正确" name="city" value="正确" v-model="city" />
      <label for="city1">正确</label>
    </div>
    <div class="field-radiobutton">
      <Checkbox inputId="错误" name="city" value="错误" v-model="city" />
      <label for="city2">错误</label>
    </div>
  </div>
</template>

<script>
export default {
  name: "Judge",
  props: {
    data: Object
  },
  data() {
    return {
      city: null,
    }
  },
  methods:{
    getMsg(){
      this.$emit("answer",this.city)
    }
  }
}
</script>

<style scoped>

</style>