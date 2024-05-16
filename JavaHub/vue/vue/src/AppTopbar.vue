<template>
	<div class="layout-topbar">
		<router-link to="/" class="layout-topbar-logo">
			<img alt="Logo" :src="topbarImage()" />
			<span>JavaHub</span>
		</router-link>
		<button class="p-link layout-menu-button layout-topbar-button" @click="onMenuToggle">
			<i class="pi pi-bars"></i>
		</button>
		<button class="p-link layout-topbar-menu-button layout-topbar-button"
			v-styleclass="{ selector: '@next', enterClass: 'hidden', enterActiveClass: 'scalein', 
			leaveToClass: 'hidden', leaveActiveClass: 'fadeout', hideOnOutsideClick: true}">
			<i class="pi pi-ellipsis-v"></i>
		</button>
		<ul class="layout-topbar-menu hidden lg:flex origin-top">
			<li>
        <Menubar :model="items" @click=onItemClick($event)><router-link :to="items.to" ></router-link></Menubar>
			</li>
		</ul>
	</div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          label:'Learn',
          icon:'pi pi-fw pi-plus',
          to:'/learn',
          click:null,
        },
        {
          label:'Exercise',
          icon:'pi pi-fw pi-file',
          to:'/exer'
        },
        {
          label:'Test',
          icon:'pi pi-fw pi-pencil',
          to:'/test'
        },
        {
          label:'User',
          icon:'pi pi-fw pi-user',
          items:[
            {
              label:'我的错题',
              icon:'pi pi-fw pi-calendar',
              to:'/wrongSet'

            },
            {
              label:'我的收藏',
              icon:'pi pi-fw pi-star',
              to:'/collec'
            },
            {
              label:'个人中心',
              icon:'pi pi-fw pi-home',
              to:'/user'
            },
            {
              label:'退出登录',
              icon:'pi pi-fw pi-power-off',
              to:'/login',
            },
          ]
        },
      ]
    }
  },
    methods: {
    onItemClick(e){
      let now= e.target.innerText
      if(now==="退出登录"){
        localStorage.removeItem("user")
        localStorage.removeItem("learn")
        localStorage.removeItem("exercise")
        localStorage.removeItem("secid")
        localStorage.removeItem("chapname")
        localStorage.removeItem("chapid")
      }
      if(now ==="Learn"){
        localStorage.setItem("learn","1")
        localStorage.setItem("exercise","0")
      } else if(now === "Exercise"){
        localStorage.setItem("learn","0")
        localStorage.setItem("exercise","1")
      } else{
        localStorage.setItem("learn","0")
        localStorage.setItem("exercise","0")
      }
    },
    onMenuToggle(event) {
            this.$emit('menu-toggle', event);
        },
		topbarImage() {
			return 'layout/images/logo-dark.svg';
		},

    },
	computed: {
		darkTheme() {
			return this.$appState.darkTheme;
		}
	}
}
</script>