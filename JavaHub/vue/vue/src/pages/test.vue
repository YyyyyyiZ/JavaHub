<template>

  <div :class="containerClass" @click="onWrapperClick">
    <AdminTopbar @menu-toggle="onMenuToggle"/>
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
import AdminTopbar from "@/AdminTopbar";
import AppMenu from "@/AppMenu";
import AppFooter from "@/AppFooter";

export default {
  name: "testad",
  emits: ['change-theme'],
  data() {
    return {
      isRouterActive:true,
      layoutMode: 'static',
      staticMenuInactive: true,
      overlayMenuActive: false,
      mobileMenuActive: false,
      menu: [
        {
            label: '后台管理系统',
            items: [
              {label: '用户管理',  to: '/admin'},
              {
                label:'知识库管理',
                items:[
                  {label: '章节管理', to: '/secad'},
                  {label: '知识管理', to: '/knowad'},
                  {label: '习题管理', to: '/exerad'},
                ]
              }
            ]
          },
      ]
    }
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
      if (event.item && !event.item.items) {
        this.overlayMenuActive = false;
        this.mobileMenuActive = false;
      }
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
    'AdminTopbar': AdminTopbar,
    'AppMenu': AppMenu,
    'AppFooter': AppFooter,
  }
}
</script>

<style scoped>

</style>