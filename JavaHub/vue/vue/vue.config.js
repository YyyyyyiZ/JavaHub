module.exports = {
	publicPath: process.env.NODE_ENV === 'production' ? '/sakai-vue' : '/'
}

module.exports={
	devServer:{
		proxy:{
			'/api':{
				target:'http://localhost:9090',
				changeOrigin:true,
				pathRewrite:{
					'/api':''
				}
			}
		}
	}
}