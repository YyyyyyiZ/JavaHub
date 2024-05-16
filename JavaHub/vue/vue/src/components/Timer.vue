<template>
  <div class="timer">
    <div>{{nowTime}}</div>
  </div>
</template>


<script>
  export default {
  name: 'Timer',
  data () {
  return {
    allseconds:0,
    timer: null,
    nowTime:"",
    hour: 0,
    minutes: 0,
    seconds: 0
}
},
  created () {
  this.timer = setInterval(this.startTimer, 1000);
},
  unmounted () {
  clearInterval(this.timer);
},

  methods: {
    reload(){
      this.$emit("time",this.allseconds)
      this.seconds=0
      this.minutes=0
      this.hour=0
      this.allseconds=0
    },
  startTimer () {
    this.allseconds +=1;
    this.seconds += 1;
    if (this.seconds >= 60) {
    this.seconds = 0;
    this.minutes= this.minutes+ 1;
  }
    if (this.minutes>= 60) {
    this.minutes= 0;
    this.hour = this.hour + 1;
  }
    this.nowTime = this.toZero(this.hour)+":"+this.toZero(this.minutes)+":"+this.toZero(this.seconds)
  },
    toZero(timeNumber) {
    return timeNumber<10?"0"+timeNumber:timeNumber
},
}
}
</script>


<style scoped>

</style>