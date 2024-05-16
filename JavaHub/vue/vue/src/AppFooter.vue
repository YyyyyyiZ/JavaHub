<template>
	<div class="layout-footer">
<!--    <el-row>-->
<!--      <span >链接</span>-->
<!--    </el-row>-->
<!--    <el-row>-->
<!--      <el-link href="https://docs.oracle.com/javase/tutorial/index.html" target="_blank">Java官方文档</el-link>-->
<!--      <el-link type="primary" href="https://docs.oracle.com/javase/tutorial/index.html" >Java官方文档</el-link>-->
<!--      <el-link type="success">success</el-link>-->
<!--      <el-link type="warning">warning</el-link>-->
<!--    </el-row>-->

    <Layout>
      <footer class="footer">
        <div class="footer-main">
          <h4 style="text-align: center;">联系我们</h4>
          <span>地址：上海市嘉定区同济大学嘉定校区曹安公路4800号 </span>
          <span> 邮编：201800</span>
<!--          <el-link type="primary" href="https://docs.oracle.com/javase/tutorial/index.html" style="border-radius:20px;">-->
<!--            Java官方文档</el-link>-->
<!--          <el-link type="success" href="https://www.runoob.com" style="border-radius:20px;">-->
<!--            菜鸟教程</el-link>-->
<!--          <el-link type="warning" href="https://www.javatpoint.com/simple-program-of-java" style="border-radius:20px;">-->
<!--            Javatpoint</el-link>-->
        </div>
      </footer>
<!--      <Header class="header" style="text-align: center;">学习链接</Header>-->
<!--			<Content class="content" style="text-align: center;">-->
<!--        <el-link type="primary" href="https://docs.oracle.com/javase/tutorial/index.html" style="border-radius:20px;">-->
<!--          Java官方文档</el-link>-->
<!--        <el-link type="success" href="https://www.runoob.com" style="border-radius:20px;">-->
<!--          菜鸟教程</el-link>-->
<!--        <el-link type="warning" href="https://www.javatpoint.com/simple-program-of-java" style="border-radius:20px;">-->
<!--          Javatpoint</el-link>-->
<!--      </Content>-->
<!--      <Footer class=" foot" style="text-align: center;"></Footer>-->
    </Layout>
  </div>
</template>

<script>
	export default {
		name: "AppFooter",
		methods: {
			footerImage() {
				return this.$appState.darkTheme ? 'images/logo-white.svg' : 'images/logo-dark.svg';
			}
		},
		computed: {
			darkTheme() {
				return this.$appState.darkTheme;
			}
		}
	}
</script>

<style lang="scss">
.el-row {
  margin-bottom: 30px;
}
.el-row:last-child {
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}

.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.layout {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.header{
  /* 0 flex-grow, 0 flex-shrink, auto flex-basis */
  flex: 0 0 auto;
}
.content {
  /* 将 flex-grow 设置为1，该元素会占用全部可使用空间
   而其他元素该属性值为0，因此不会得到多余的空间*/
  flex:1 0 auto;
}
.foot {
  flex:0 0 auto;
}
</style>