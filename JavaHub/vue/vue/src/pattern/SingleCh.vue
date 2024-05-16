<template>
  <div >
    <p>{{data.exerques}}</p>
    <div v-for="category of data.choice" :key="category" class="field-radiobutton">
      <Checkbox :inputId="category" :name="category" :value="category" v-model="selectedCategory"/>
      <label :for="category">{{category}}</label>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    data: Object
  },
  name: "SingleCh",
  data() {
    return {
      selectedCategory: null
    }
  },
  methods:{
    getMsg(){
      this.$emit("answer",this.selectedCategory)
    }
  }
}
</script>

<style scoped>

</style>