<template>
  <div :class="containerClass" @click="onWrapperClick">
    <AppTopBar @menu-toggle="onMenuToggle"/>
    <div class="layout-sidebar" @click="onSidebarClick">
      <AppMenu :model="menu" @menuitem-click="onMenuItemClick"/>
    </div>
    <div class="layout-main-container">
      <div class="layout-main">
        <router-view v-if="isRouterActive"/>
      </div>
      <AppFooter/>
    </div>
    <transition name="layout-mask">
      <div class="layout-mask p-component-overlay" v-if="mobileMenuActive"></div>
    </transition>
  </div>
</template>

<script>
import AppTopBar from './AppTopbar.vue';
import AppMenu from './AppMenu.vue';
import AppFooter from './AppFooter.vue';

export default {
  emits: ['change-theme'],
  data() {
    return {
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      isRouterActive:true,
      layoutMode: 'static',
      staticMenuInactive: true,
      overlayMenuActive: false,
      mobileMenuActive: false,
      menu: [
        {
          label: 'Home',
          items: [{
            label: 'Dashboard', icon: 'pi pi-fw pi-home', to: '/'
          }]
        },
        {
          label: 'JAVA语言程序设计',
          items: [
            {
              label: '第1章 Java语言概述', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '1.1 Java语言的历史', icon: 'pi pi-fw pi-bookmark'},
                {label: '1.2 Java语言的特点', icon: 'pi pi-fw pi-bookmark'},
                {label: '1.3 Java程序及其实现', icon: 'pi pi-fw pi-bookmark'},
                {label: '1.4 Java Application程序的实现', icon: 'pi pi-fw pi-bookmark'},
                {label: '1.5 Java Application程序的不同界面输出', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第2章 Java语言语法基础', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '2.1 词法规则', icon: 'pi pi-fw pi-bookmark'},
                {label: '2.2 数据类型', icon: 'pi pi-fw pi-bookmark'},
                {label: '2.3 常量和变量', icon: 'pi pi-fw pi-bookmark'},
                {label: '2.4 运算符和表达式', icon: 'pi pi-fw pi-bookmark'},
                {label: '2.5 语句', icon: 'pi pi-fw pi-bookmark'},
                {label: '2.6 数组和字符串', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第3章 Java语言面向对象的特征', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '3.1 面向对象的基本概念', icon: 'pi pi-fw pi-bookmark'},
                {label: '3.2 类的定义', icon: 'pi pi-fw pi-bookmark'},
                {label: '3.3 对象', icon: 'pi pi-fw pi-bookmark'},
                {label: '3.4 类的继承和多态', icon: 'pi pi-fw pi-bookmark'},
                {label: '3.5 接口和包', icon: 'pi pi-fw pi-bookmark'},
                {label: '3.6 常用的工具类简介', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第4章 Java Applet及其应用', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '4.1 Applet概述', icon: 'pi pi-fw pi-bookmark'},
                {label: '4.2 Applet与Applet类', icon: 'pi pi-fw pi-bookmark'},
                {label: '4.3 Applet程序和HTML文件', icon: 'pi pi-fw pi-bookmark'},
                {label: '4.4 Applet的应用', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第5章 图形用户界面设计', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '5.1 图形用户界面概述', icon: 'pi pi-fw pi-bookmark'},
                {label: '5.2 事件和事件处理', icon: 'pi pi-fw pi-bookmark'},
                {label: '5.3 基本控制组件的使用', icon: 'pi pi-fw pi-bookmark'},
                {label: '5.4 布局设计', icon: 'pi pi-fw pi-bookmark'},
                {label: '5.5 常用容器组件的使用', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第6章 异常和异常处理', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '6.1 异常和异常类', icon: 'pi pi-fw pi-bookmark'},
                {label: '6.2 异常处理', icon: 'pi pi-fw pi-bookmark'},
                {label: '6.3 创建异常', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第7章 线程', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '7.1 概念', icon: 'pi pi-fw pi-bookmark'},
                {label: '7.2 线程的创建', icon: 'pi pi-fw pi-bookmark'},
                {label: '7.3 线程的状态', icon: 'pi pi-fw pi-bookmark'},
                {label: '7.4 线程的同步', icon: 'pi pi-fw pi-bookmark'},
                {label: '7.5 线程的其他特性', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第8章 Java语言的集合操作', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '8.1 概念', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.2 数组（Arrays）类', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.3 基本的集合接口', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.4 集合（Set）', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.5 列表（List）', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.6 映射（Map）', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.7 Collections类', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.8 枚举和迭代', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.9 历史集合类', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.10 使用原则', icon: 'pi pi-fw pi-bookmark'},
                {label: '8.11 Java泛型初探', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第9章 Java输入/输出', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '9.1 流的概念', icon: 'pi pi-fw pi-bookmark'},
                {label: '9.2 输入/输出类', icon: 'pi pi-fw pi-bookmark'},
                {label: '9.3 标准输入/输出', icon: 'pi pi-fw pi-bookmark'},
                {label: '9.4 文件读/写', icon: 'pi pi-fw pi-bookmark'},
                {label: '9.5 流的包装', icon: 'pi pi-fw pi-bookmark'},
                {label: '9.6 文件类', icon: 'pi pi-fw pi-bookmark'},
                {label: '9.7 随机访问文件', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:null,
            },
            {
              label: '第10章 Java网络编程', icon: 'pi pi-fw pi-flag',
              items: [
                {label: '10.1 概述', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.2 URL访问', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.3 InetAddress类', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.4 NetworkInterface类', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.5 Socket应用', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.6 安全套接字应用', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.7 UDP数据报', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.8 组播', icon: 'pi pi-fw pi-bookmark'},
                {label: '10.9 对IPv6的支持', icon: 'pi pi-fw pi-bookmark'},
              ],
              disabled:true,
            },
            {
              label:'学习须知',icon: 'pi pi-fw pi-flag',to:'/guide',
              disabled:false,
            }
          ]
        },
      ]
    }
  },
  mounted() {
    this.request.post("learnrecord/menu",this.user).then(res => {
      for (let i=0;i<10;i++){
        this.menu[1].items[i].disabled=res[i];
      }
    })
  },
  methods: {
    reload(){
      this.isRouterActive=false;
      this.$nextTick(function (){
        this.isRouterActive=true
      })
    },
    onWrapperClick() {
      if (!this.menuClick) {
        this.overlayMenuActive = false;
        this.mobileMenuActive = false;
      }
      this.menuClick = false;
    },
    onMenuToggle() {
      this.menuClick = true;
      if (this.isDesktop()) {
        if (this.layoutMode === 'overlay') {
          if (this.mobileMenuActive === true) {
            this.overlayMenuActive = true;
          }
          this.overlayMenuActive = !this.overlayMenuActive;
          this.mobileMenuActive = false;
        } else if (this.layoutMode === 'static') {
          this.staticMenuInactive = !this.staticMenuInactive;
        }
      } else {
        this.mobileMenuActive = !this.mobileMenuActive;
      }
      event.preventDefault();
    },
    onSidebarClick() {
      this.menuClick = true;
    },
    onMenuItemClick(event) {
      let secid=localStorage.getItem("secid")
      let obj=JSON.stringify({
        "userid":this.user.userid,
        "secid":secid,
      })
      this.request.post("/learnrecord/insert",obj)
      if (event.item && !event.item.items) {
        this.overlayMenuActive = false;
        this.mobileMenuActive = false;
      }
      this.request.post("/learnrecord/menu",this.user).then(res => {
        for (let i=0;i<10;i++){
          console.log(this.menu[1].items[i])
          this.menu[1].items[i].disabled=res[i];
        }
      })

    },
    isDesktop() {
      return window.innerWidth >= 992;
    },
  },
  computed: {
    containerClass() {
      return ['layout-wrapper', {
        'layout-overlay': this.layoutMode === 'overlay',
        'layout-static': this.layoutMode === 'static',
        'layout-static-sidebar-inactive': this.staticMenuInactive && this.layoutMode === 'static',
        'layout-overlay-sidebar-active': this.overlayMenuActive && this.layoutMode === 'overlay',
        'layout-mobile-sidebar-active': this.mobileMenuActive,
        'p-input-filled': this.$primevue.config.inputStyle === 'filled',
        'p-ripple-disabled': this.$primevue.config.ripple === false
      }];
    },
  },
  components: {
    'AppTopBar': AppTopBar,
    'AppMenu': AppMenu,
    'AppFooter':AppFooter,
  }
}
</script>

<style lang="scss">
@import './App.scss';

</style>
