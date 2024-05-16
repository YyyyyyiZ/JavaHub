<template>
  <div>
    <p>{{ data.exerques }}</p>
    <div v-for="category of data.choice" :key="category" class="field-checkbox">
      <Checkbox :inputId="category" name="category" :value="category" v-model="selectedCategories"/>
      <label :for="category">{{category}}</label>
    </div>
  </div>
</template>
<script>
export default {
  name: "MultiCh",
  props: {
    data: Object
  },
  data() {
    return {
      selectedCategories: []
    }
  },
  methods:{
    getMsg(){
      this.$emit("answer",this.selectedCategories)
    }
  }
}
</script>
<style scoped>
</style>