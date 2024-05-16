<template>
  <div class="grid p-fluid">
    <div class="col-12 lg:col-6">
      <div class="card">
        <h5>学习时长</h5>
        <Chart type="line" :data="lineData" :options="lineOptions" />
      </div>

<!--      个人学习时长-->
      <div class="card flex flex-column align-items-center" >
        <h5 class="align-self-start">时间分配</h5>
        <Chart type="pie" :data="pieData1" :options="pieOptions" style="width: 50%" />
      </div>


      <div class="card flex flex-column align-items-center" v-if="radar1">
        <h5 class="align-self-start">章节掌握</h5>
        <Chart type="radar" :data="radarData1" :options="radarOptions" style="width: 50%" />
      </div>
    </div>

    <div class="col-12 lg:col-6">
      <div class="card">
        <h5>练习情况</h5>
        <Chart type="bar" :data="barData" :options="barOptions" />
      </div>

      <div class="card flex flex-column align-items-center">
        <h5 class="align-self-start">题量分配</h5>
        <Chart type="doughnut" :data="pieData2" :options="pieOptions" style="width: 50%" />
      </div>

      <div class="card flex flex-column align-items-center" v-if="radar2">
        <h5 class="align-self-start">知识掌握</h5>
        <Chart type="radar" :data="radarData2" :options="radarOptions" style="width: 50%" />
      </div>
    </div>
  </div>
</template>

<script>
import EventBus from '@/AppEventBus';
import request from "@/utils/request";
import router from "@/router";

export default {
  themeChangeListener: null,
  mounted() {
    request.post("/exercise/dashchap").then(res=>{
      console.log(res)
      this.pieData2.datasets[0].data=res
      console.log(this.pieData2.datasets[0].data);
    });
    this.themeChangeListener = (event) => {
      if (event.dark)
        this.applyDarkTheme();
      else
        this.applyLightTheme();
    };
    EventBus.on('change-theme', this.themeChangeListener);

    if (this.isDarkTheme()) {
      this.applyDarkTheme();
    }
    else {
      this.applyLightTheme();
    }
  },
  beforeUnmount() {
    EventBus.off('change-theme', this.themeChangeListener );
  },
  data() {
    return {
      user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
      radar1:true,
      radar2:true,
      lineData: {
        labels: [],
        datasets: [
          {
            label: '分钟',
            data: [],
            fill: false,
            backgroundColor: "#99CCFF",
            borderColor: "#99CCFF",
            tension: .4
          },
          {
            label: '分钟',
            data: [],
            fill: false,
            backgroundColor: "#FFCCCC",
            borderColor: "#FFCCCC",
            tension: .4
          },
        ]
      },
      pieData1: {
        labels: ['1','2','3','4','5','6','7','8','9','10'],
        datasets: [
          {
            data: [],
            backgroundColor: [
              "#f0ebe5",
              "#cac3bb",
              "#a6a6a8",
              "#7a7281",
              "#a27e7e",
              "#7b8b6f",
              "#8696a7",
              "#965454",
              "#656565",
              "#d8caaf",
            ],
            hoverBackgroundColor: [
              "#f0ebe5",
              "#cac3bb",
              "#a6a6a8",
              "#7a7281",
              "#a27e7e",
              "#7b8b6f",
              "#8696a7",
              "#965454",
              "#656565",
              "#d8caaf",
            ]
          }
        ]
      },
      // 题量分配
      pieData2: {
        labels: ['1', '2', '3','4','5','6','7','8','9','10'],
        datasets: [
          {
            data:[],
            backgroundColor: [
              "#f0ebe5",
              "#cac3bb",
              "#a6a6a8",
              "#7a7281",
              "#a27e7e",
              "#7b8b6f",
              "#8696a7",
              "#965454",
              "#656565",
              "#d8caaf",
            ],
            hoverBackgroundColor: [
              "#f0ebe5",
              "#cac3bb",
              "#a6a6a8",
              "#7a7281",
              "#a27e7e",
              "#7b8b6f",
              "#8696a7",
              "#965454",
              "#656565",
              "#d8caaf",
            ]
          }
        ]
      },
      radarData1: {
        labels: [],
        datasets: [
          {
            label: '薄弱章节',
            backgroundColor: 'rgba(179,181,198,0.2)',
            borderColor: 'rgba(179,181,198,1)',
            pointBackgroundColor: 'rgba(179,181,198,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(179,181,198,1)',
            data: [],
          },
        ]
      },
      barData: {
        labels: [],
        datasets: [
          {
            label: '做题数',
            backgroundColor: '#99CCFF',
            data: []
          },
          {
            label: '正确数',
            backgroundColor: '#FFCCCC',
            data: []
          }
        ]
      },
      radarData2: {
        labels: [],
        datasets: [
          {
            label:"薄弱知识点",
            backgroundColor: 'rgba(255,99,132,0.2)',
            borderColor: 'rgba(255,99,132,1)',
            pointBackgroundColor: 'rgba(255,99,132,1)',
            pointBorderColor: '#fff',
            pointHoverBackgroundColor: '#fff',
            pointHoverBorderColor: 'rgba(255,99,132,1)',
            data: []
          },
        ]
      },
      lineOptions: null,
      pieOptions: null,
      polarOptions: null,
      barOptions: null,
      radarOptions: null
    }
  },
  created() {
    let userJson = localStorage.getItem("user")
    if(!userJson){
      router.push("/login")
      return
    }
    this.load();
    this.Today();
    // this.tableDataAnother=this.tableData
  },
  methods: {
    load(){
      request.post("/exercise/dashchap").then(res=>{
        this.pieData2.datasets[0].data=res;
      });

      // request.post("/learnrecord/all").then(res=>{
      //   this.alltime1=res;
      // });

      // 时间分配
      request.post("/learnrecord/"+this.user.userid).then(res=>{
        // this.alltime0=res;
        // if(this.alltime0.length==0){
        //   this.pieData1.datasets[0].data=this.alltime1;
        // }else{
        //   this.pieData1.datasets[0].data=this.alltime0;
        // }
        // console.log(res);
        this.pieData1.datasets[0].data=res;
        // console.log(this.alltime1);
        // console.log(this.alltime0);
      });

      let myDate = new Date();
      myDate.setDate(myDate.getDate() - 6);
      const dateArray = [];
      for (let i = 0; i < 7; i++) {
        const myDate1=myDate.toLocaleDateString().replace('/','-');
        const myDate2=myDate1.replace('/','-');
        dateArray.push(myDate2);
        myDate.setDate(myDate.getDate() + 1);
      }
      // console.log(dateArray);
      request.post("/user/time/"+this.user.userid+"/"+dateArray).then(res=>{
        console.log(res);
        this.lineData.datasets[0].data=res;
      });
      request.post("/user/time/"+dateArray).then(res=>{
        console.log(res);
        this.lineData.datasets[1].data=res;
      });
      // 做题数
      request.post("/userl/count/"+this.user.userid+"/"+dateArray).then(res=>{
        console.log(res);
        this.barData.datasets[0].data=res;
      });
      // 正确数
      request.post("/userl/counttrue/"+this.user.userid+"/"+dateArray).then(res=>{
        console.log(res);
        this.barData.datasets[1].data=res;
      });
      // 薄弱知识
      request.post("/userl/lowknow/"+this.user.userid).then(res=>{
        console.log(res);
        this.radarData2.labels=res;
      });
      // 薄弱知识点
      request.post("/userl/lowpoint/"+this.user.userid).then(res=>{
        console.log(res);
        this.radarData2.datasets[0].data=res;
        if (this.radarData2.datasets[0].data.length==0){
          this.radar2 = false;
        }
      });
      // 薄弱章节
      request.post("/userl/lowsec/"+this.user.userid).then(res=>{
        console.log(res);
        this.radarData1.labels=res;
      });
      // 薄弱章节数
      request.post("/userl/lowsecpoint/"+this.user.userid).then(res=>{
        console.log("radar"+res);
        this.radarData1.datasets[0].data=res;
        if (this.radarData1.datasets[0].data.length==0){
          this.radar1 = false;
        }
      });
      // request.post("/userl/lowsecpoint").then(res=>{
      //   console.log(res);
      //   this.radarData1.datasets[1].data=res;
      // });

    },
    Today(){
      let myDate = new Date();
      myDate.setDate(myDate.getDate() - 6);
      const dateArray = [];
      for (let i = 0; i < 7; i++) {
        dateArray.push(myDate.toLocaleDateString());
        myDate.setDate(myDate.getDate() + 1);
      }
      this.lineData.labels=dateArray;
      this.barData.labels=dateArray;
    },
    isDarkTheme() {
      return this.$appState.darkTheme === true;
    },
    applyLightTheme() {
      this.lineOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#495057'
            }
          }
        },
        scales: {
          x: {
            ticks: {
              color: '#495057'
            },
            grid: {
              color:  '#ebedef',
            }
          },
          y: {
            ticks: {
              color: '#495057'
            },
            grid: {
              color:  '#ebedef',
            }
          },
        }
      };

      this.barOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#495057'
            }
          }
        },
        scales: {
          x: {
            ticks: {
              color: '#495057'
            },
            grid: {
              color:  '#ebedef',
            }
          },
          y: {
            ticks: {
              color: '#495057'
            },
            grid: {
              color:  '#ebedef',
            }
          },
        }
      };

      this.pieOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#495057'
            }
          }
        }
      };

      this.polarOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#495057'
            }
          }
        },
        scales: {
          r: {
            grid: {
              color: '#ebedef'
            }
          }
        }
      };

      this.radarOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#495057'
            }
          }
        },
        scales: {
          r: {
            grid: {
              color: '#ebedef'
            }
          }
        }
      };

    },
    applyDarkTheme() {
      this.lineOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#ebedef'
            }
          }
        },
        scales: {
          x: {
            ticks: {
              color: '#ebedef'
            },
            grid: {
              color:  'rgba(160, 167, 181, .3)',
            }
          },
          y: {
            ticks: {
              color: '#ebedef'
            },
            grid: {
              color:  'rgba(160, 167, 181, .3)',
            }
          },
        }
      };

      this.barOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#ebedef'
            }
          }
        },
        scales: {
          x: {
            ticks: {
              color: '#ebedef'
            },
            grid: {
              color:  'rgba(160, 167, 181, .3)',
            }
          },
          y: {
            ticks: {
              color: '#ebedef'
            },
            grid: {
              color:  'rgba(160, 167, 181, .3)',
            }
          },
        }
      };

      this.pieOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#ebedef'
            }
          }
        }
      };

      this.polarOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#ebedef'
            }
          }
        },
        scales: {
          r: {
            grid: {
              color: 'rgba(160, 167, 181, .3)'
            }
          }
        }
      };

      this.radarOptions = {
        plugins: {
          legend: {
            labels: {
              color: '#ebedef'
            }
          }
        },
        scales: {
          r: {
            grid: {
              color: 'rgba(160, 167, 181, .3)'
            }
          }
        }
      };
    }
  }
}
</script>