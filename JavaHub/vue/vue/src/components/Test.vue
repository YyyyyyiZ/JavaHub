<template>
  <div>
    <div class="card">
      <Carousel :value="products" :numVisible="4" :numScroll="4" :responsiveOptions="responsiveOptions">
        <template #header>
          <h4>Test</h4>
        </template>
        <template #item="slotProps">
          <div class="product-item">
            <div class="product-item-content">
              <h5 class="mt-0 mb-3">第{{slotProps.data.chapid}}章</h5>
              <div class="mb-3">
                <img src="https://www.primefaces.org/wp-content/uploads/2020/05/placeholder.png" :alt="slotProps.data.name" class="product-image" />
              </div>
              <div>
                <h4 class="mb-1">{{slotProps.data.name}}</h4>
                <div class="car-buttons mt-5">
                  <Button icon="pi pi-search" :id=slotProps.data.chapid :value=slotProps.data.name  class="p-button p-button-rounded mr-2" @click="detail($event)"/>
                  <Button icon="pi pi-pencil"  :id=slotProps.data.chapid :value=slotProps.data.name class="p-button-success p-button-rounded mr-2" @click="newTest($event)" />
                </div>
              </div>
            </div>
          </div>
        </template>
      </Carousel>
    </div>
  </div>
</template>

<script>

import router from "@/router";

export default {
  data() {
    return {
      responsiveOptions: [
        {breakpoint: '1024px', numVisible: 3, numScroll: 3},
        {breakpoint: '600px', numVisible: 2, numScroll: 2},
        {breakpoint: '480px', numVisible: 1, numScroll: 1}
      ],
      products: [
        {"chapid": "1", "name": "Java语言概述", "description": "Product Description", "image": "bamboo-watch.jpg"},
        {"chapid": "2", "name": "Java语言语法基础", "description": "Product Description", "image": "black-watch.jpg"},
        {"chapid": "3", "name": "Java语言面向对象的特征", "description": "Product Description", "image": "blue-band.jpg"},
        {"chapid": "4", "name": "Java Applet及其应用", "description": "Product Description", "image": "blue-t-shirt.jpg"},
        {"chapid": "5", "name": "图形用户界面设计", "description": "Product Description", "image": "bracelet.jpg"},
        {"chapid": "6", "name": "异常和异常处理", "description": "Product Description", "image": "chakra-bracelet.jpg"},
        {"chapid": "7", "name": "线程", "description": "Product Description", "image": "galaxy-earrings.jpg"},
        {"chapid": "8", "name": "Java语言的集合操作", "description": "Product Description", "image": "game-controller.jpg"},
        {"chapid": "9", "name": "Java输入/输出", "description": "Product Description", "image": "gaming-set.jpg"},
        {"chapid": "10", "name": "Java网络编程", "description": "Product Description", "image": "gaming-set.jpg"}
      ]
    }
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
  },
  methods:{
    detail(e){
      let id=e.currentTarget.id
      let value=e.currentTarget.value
      localStorage.setItem("chapid",id)
      localStorage.setItem("chapname",value)
      this.$router.push("/testDetail")
    },
    newTest(e){
      let id=e.currentTarget.id
      let value=e.currentTarget.value
      localStorage.setItem("chapid",id)
      localStorage.setItem("chapname",value)
      this.$router.push("/doTest")
    }
  }
}
</script>

<style lang="scss" scoped>
.product-item {
  .product-item-content {
    border: 1px solid var(--surface-border);
    border-radius: 3px;
    margin: .3rem;
    text-align: center;
    padding: 2rem 0;
  }
  .product-image {
    width: 50%;
    box-shadow: 0 3px 6px rgba(0, 0, 0, 0.16), 0 3px 6px rgba(0, 0, 0, 0.23)
  }
}
</style>