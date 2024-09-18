<template>
  <div class="container swap" :class="skin">
    <div class="main">
      <div class="content">
         <div class="up">
          <div class="left">
            <div class="coin-menu">
              <div style="padding: 8px 10px;height:48px;">
                <Input search :placeholder="$t('common.searchplaceholderswap')" @on-change="seachInputChange"
                   v-model="searchKey" />
              </div>
              <Table @on-current-change="gohref" highlight-row :columns="coins.columns" ></Table>
            </div>
          </div>

          <div class="center">
            <div class="symbol">

            </div>
            <div class="imgtable">
              <div class="handler">
                <span @click="changeImgTable('k')" :class="{active:currentImgTable==='k'}">{{$t("swap.kline")}}</span>
                <span @click="changeImgTable('s')" :class="{active:currentImgTable==='s'}">{{$t("swap.depth")}}</span>
              </div>
              <div id="kline_container" :class="{hidden:currentImgTable==='s'}">
              </div>
              <DepthGraph :class="{hidden:currentImgTable==='k'}" ref="depthGraph"></DepthGraph>
            </div>
          </div>

        </div>

        <div class="order" >
          <div class="order-handler">
            <span @click="changeOrder('cposition')" :class="{active:selectedOrder==='cposition'}">{{$t('swap.currentposition')}}</span>
            <span @click="changeOrder('current')" :class="{active:selectedOrder==='current'}">{{$t('swap.curdelegation')}}</span>
            <span @click="changeOrder('history')" :class="{active:selectedOrder==='history'}">{{$t('swap.hisdelegation')}}</span>
            <router-link v-show="selectedOrder==='cposition'" class="linkmore" to="/uc/entrust/currentposition">{{$t("common.more")}}</router-link>
            <router-link v-show="selectedOrder==='current'" class="linkmore" to="/uc/entrust/current">{{$t("common.more")}}</router-link>
            <router-link v-show="selectedOrder==='history'" class="linkmore" to="/uc/entrust/history">{{$t("common.more")}}</router-link>
          </div>
          <div class="table">
            <Table height="240" v-if="selectedOrder==='cposition'" :columns="currentPosition.columns" :data="currentPosition.rows" :no-data-text="$t('common.nodata')"></Table>
            <Table height="240" v-else-if="selectedOrder==='current'" :columns="currentOrder.columns" :data="currentOrder.rows" :no-data-text="$t('common.nodata')"></Table>
            <Table height="240" v-else :columns="historyOrder.columns" :no-data-text="$t('common.nodata')" :data="historyOrder.rows"></Table>
          </div>
        </div>

      </div>

      <div class="right">
        <div class="right_up">
          <div class="plat">
            <div class="handlers">
              <span @click="changePlate('all')" class="handler handler-all" :class="{active:selectedPlate=='all'}"></span>
              <span @click="changePlate('buy')" class="handler handler-green" :class="{active:selectedPlate=='buy'}"></span>
              <span @click="changePlate('sell')" class="handler handler-red" :class="{active:selectedPlate=='sell'}"></span>
            </div>
            <Table v-show="selectedPlate!='buy'" @on-current-change="buyPlate" highlight-row ref="currentRowTable" class="sell_table" :columns="plate.columns" :data="plate.askRows" :no-data-text="$t('common.nodata')"></Table>
            <div class="plate-nowprice">
              <span class="price" :class="{buy:currentCoin.change>0,sell:currentCoin.change<0}">{{currentCoin.price | toFixed(baseCoinScale)}}</span>
              <span v-if="currentCoin.change>0" class="buy">↑</span>
              <span v-else-if="currentCoin.change<0" class="sell">↓</span>
              <span class="price-cny"> ≈ {{currentCoin.usdRate*CNYRate | toFixed(2)}} CNY</span>
            </div>
            <Table v-show="selectedPlate!='sell'" @on-current-change="sellPlate" highlight-row class="buy_table" :class="{hidden:selectedPlate==='all'}" :columns="plate.columns" :data="plate.bidRows" :no-data-text="$t('common.nodata')"></Table>

          </div>
          <div class="trade">
            <Table height="264" :columns="trade.columns" :data="trade.rows" :no-data-text="$t('common.nodata')"></Table>
          </div>
        </div>

        <div class="right_down">
          <div class="operate">
            <div class="oper_menu">
              <span @click="open_position" :class="{active:!closePostion}">{{$t("swap.open")}}</span>
              <span @click="close_position" :class="{active:closePostion}">{{$t("swap.close")}}</span>
            </div>
            <div class="oper_menu">
              <span @click="changeOperatePrice('limitedPrice')" :class="{active:operatePrice==='limitedPrice'}">{{$t("swap.limited_price")}}</span>
              <span @click="changeOperatePrice('marketPrice')" :class="{active:operatePrice==='marketPrice'}">{{$t("swap.market_price")}}</span>
			        <span @click="changeOperatePrice('spotPrice')" :class="{active:operatePrice==='spotPrice'}">{{$t("swap.spot_price")}}</span>
            </div>


          </div>
          <div class="cinfo">




          </div>

        </div>

      </div>

    </div>
  </div>
</template>
<style lang="scss">
.item_fit{
  height: 700px;
  overflow-y: scroll;
  &::-webkit-scrollbar {
    width: 0px;
  }
  &::-webkit-scrollbar-thumb {
    background: transparent;
    border-radius: 0;
  }
  &::-webkit-scrollbar-track-piece {
    background: transparent;
    overflow: hidden;
  }
}
.tabBar {
  a {
    color: #f0a70a;
  }

  .ivu-tabs-nav-scroll {
    padding-left: 12px;
  }

  .ivu-tabs-nav .ivu-tabs-tab-active {
    color: #EFA70B;
  }

  .ivu-tabs-ink-bar {
    background-color: #EFA70B;
  }

  .swap .symbol .item .coin {
    font-size: 12px;
  }

  .trade_bd {
    padding: 0 15px;
  }

  .trade_menu {
    margin-left: 10px;
  }

  .ivu-tooltip-content {
    position: absolute;
    left: 12px;
    top: -20px;
  }

  .trade_wrap .ivu-form-item-content input {
    padding-left: 25px;
  }

  .symbol {
    padding: 2px 10px !important;
    margin-bottom: 2px !important;
  }

  .imgtable .handler {
    margin-left: 10px;
  }

  .imgtable .mflag {
    z-index: 100;
  }

  .item {
    .price-cny {
      font-size: 12px;
      color: #546886;
    }

    .coin {
      font-size: 14px !important;
    }
  }
}
.xs_table {
  width: 100%;
  overflow-x: scroll;

  &::-webkit-scrollbar {
    height: 2px;
  }



  &::-webkit-scrollbar-track-piece {
    background: transparent;
  }

}

#kline_container{
    background: #192330;
}


</style>
<style scoped lang="scss">
@import url(../../assets/css/swap.css);
$night-bg: #0b1520;
$night-headerbg: #27313e;
$night-contentbg: #192330;
$night-color: #fff;

.swap .ivu-tooltip-inner {
  padding: 2px 5px !important;
  min-height: 24px !important;
}

.swap {
  color: #fff;
  background-color: #0b1520;

  .main {
    display: flex;
    flex-direction: row;
    margin-top: 5px;

    .content {
      flex: 0 0 70%;
      margin-right: 5px;
      justify-content: space-between;

      .up {
        display: flex;
        flex-wrap: wrap;

        .left {
          flex: 0 0 20%;
          margin-right: 5px;

          .coin-menu {
            height: 460px;
            background-color: #192330;
            margin-bottom: 10px;
            border-radius: 0px;
          }
        }

        .center {
          position: relative;
          flex: 0 0 79.5%;

          .imgtable {
            height: 91%;
            position: relative;
            overflow: hidden;
            margin-bottom: 5px;
            .handler {
              position: absolute;
              top: 10px;
              right: 40px;
              z-index: 1000;
              > span {
                background-color: #2c3b59;
                color: #c7cce6;
                padding: 4px 8px;
                cursor: pointer;
                font-size: 13px;
                opacity: 0.5;
                &.active {
                  opacity: 1;
                }
              }
            }
          }
        }
      }

      .order {
        position: relative;
        flex: 0 0 98.6%;
        margin-left: 0.5%;
        min-height: 227px;
        margin-bottom: 10px;
        .order-handler {
          // background-color: #192330;
          background-color: #192330;
          font-size: 0;
          border-radius: 6px;
          // line-height: 38px;
          > span {
            padding: 0 20px;
            font-size: 14px;
            display: inline-block;
            color: #fff;
            cursor: pointer;
            line-height: 40px;
            background-color: #192330;
            &.active {
              color: #f0a70a;
              background-color: #27313e;
            }
            &:first-child {
              border-top-left-radius: 0px;
            }
            &:last-child {
              border-top-right-radius: 0px;
            }
          }
        }
      }

    }

    .right {
      flex: 0 0 30%;

      .right_up {
        display: flex;
        flex-direction: row;

        .plat {
          flex: 0 0 49%;
          margin-right: 5px;

          .handler {
            display: inline-block;
            margin-right: 10px;
            width: 20px;
            height: 20px;
            background-size: 100% 100%;
            cursor: pointer;
            &.handler-all {
              background-image: url("../../assets/images/exchange/plate_all.png");
              &.active {
                background-image: url("../../assets/images/exchange/plate_all_active.png");
              }
            }
            &.handler-green {
              background-image: url("../../assets/images/exchange/plate_green.png");
              &.active {
                background-image: url("../../assets/images/exchange/plate_green_active.png");
              }
            }
            &.handler-red {
              background-image: url("../../assets/images/exchange/plate_red.png");
              &.active {
                background-image: url("../../assets/images/exchange/plate_red_active.png");
              }
            }
          }

          .plate-nowprice {
            text-align: center;
            background-color: #27313e;
            line-height: 1;
            display: flex;
            align-items: center;
            height: 40px;
            justify-content: center;
            font-size: 14px;
            font-weight: 500;
            .price {
              font-size: 18px;
              margin-right: 10px;
            }
            .price-cny {
              font-size: 12px;
              margin-left: 10px;
              font-weight: 400;
              color: rgba(219, 222, 246, 0.3);
            }
          }

        }
      
        .trade {
          flex: 0 0 49%;
          margin-right: 5px;


        }
      }

      .right_down {
        flex-direction: row;

        .operate {
          flex: 0 0 49%;
          margin-right: 5px;

          .oper_menu {
            position: relative;
            background-color: #192330;
            border-top-left-radius: 0px;
            border-top-right-radius: 0px;
            border-bottom: 1px solid #27313e;
            font-size: 0;
            height: 40px;
            line-height: 40px;
            > span {
              font-size: 16px;
              padding: 11px 20px;
              cursor: pointer;
              &.active {
                color: #f0a70a;
                border-bottom: 2px solid #f0a70a;
              }
              &:first-child {
                border-top-left-radius: 0px;
              }
            }
            .fee-wrap {
              position: absolute;
              top: 0;
              right: 20px;
              font-size: 12px;
              > span {
                margin-right: 10px;
                color: #7c7f82;
              }
              > a {
                vertical-align: middle;
              }
            }
          }

        }

        .cinfo {
          flex: 0 0 49%;
          margin-right: 5px;

          
        }
      }

      
    }
  }

  .symbol {
    display: flex;
    justify-content: space-between;
    padding: 15px 30px;
    margin-bottom: 5px;
    align-items: center;
    background-color: #192330;
    line-height: 1;
    border-radius: 0px;
    .item {
      .price-cny {
        font-size: 12px;
        color: #546886;
      }
      .coin {
        font-size: 20px;
      }
      .text {
        width: 100%;
        display: block;
        font-size: 12px;
        color: #999;
        margin-bottom: 5px;
      }
      .num {
        font-size: 12px;
        color: #fff;
      }
      > img {
        display: block;
        width: 18px;
        height: 18px;
        cursor: pointer;
      }
    }
  }
}


</style>

<script>
import expandRow from "@components/exchange/expand.vue";
import Datafeeds from "@js/charting_library/datafeed/bitrade.js";
var Stomp = require("stompjs");
var SockJS = require("sockjs-client");

import DepthGraph from "@components/exchange/DepthGraph.vue";
import $ from "@js/jquery.min.js";

export default {
  components: {  DepthGraph },
  // reactive state
  data() {
    let self = this;
    return {
      sliderStep: [25, 50, 75, 100],
      sliderBuyLimitPercent: 0,

      skin: "night", //皮肤样式day&night
      currentImgTable: "k",
      selectedOrder: "cposition", //当前显示的持仓记录
      selectedPlate: "all", //当前显示的买卖盘

      datafeed: null,
      defaultPath: "btc_usdt",
      basecion: "usdt", 

      searchKey: "",
      coinInfo:{

      },
      currentCoin: {
        base: "",
        coin: "",
        symbol: ""
      },


      coins: {

        currentImgTable: "k",

        columns: [
          {
            title: this.$t("swap.contract")+"/"+this.$t("swap.vol"),
            key: "contract",
            sortable: false,
            width: 120,
            className: "coin-menu-symbol",
            render: (h, params) => {
              if(params.row.coin == "BZB"){
                return h("div", [
                  h("Icon", {
                    props: {
                      // color:"red",
                      type: params.row.isFavor
                        ? "ios-star"
                        : "ios-star-outline"
                    },
                    nativeOn: {
                      click: () => {
                        event.stopPropagation(); //阻止事件冒泡
                        if (this.isLogin) {
                          if (
                            event.currentTarget.className ==
                            "ivu-icon ivu-icon-ios-star"
                          ) {
                            this.cancelCollect(params.index, params.row);
                            event.currentTarget.className ==
                              "ivu-icon ivu-icon-ios-star-outline";
                          } else {
                            this.collect(params.index, params.row);
                            event.currentTarget.className =
                              "ivu-icon ivu-icon-ios-star";
                          }
                        } else {
                          this.$Message.warning("请先登录");
                        }
                      }
                    }
                  }),
                  h("span", params.row.coin),
                  h("span", {
                    style: {fontSize: "8px", padding: "2px 5px 1px 5px", color: "#FFF", marginLeft: "5px", background:"#F30", borderRadius:"4px"}
                  }, "HOT")
                ]);
              }
              return h("div", [
                h("Icon", {
                  props: {
                    // color:"red",
                    type: params.row.isFavor
                      ? "ios-star"
                      : "ios-star-outline"
                  },
                  nativeOn: {
                    click: () => {
                      event.stopPropagation(); //阻止事件冒泡
                      if (this.isLogin) {
                        if (
                          event.currentTarget.className ==
                          "ivu-icon ivu-icon-ios-star"
                        ) {
                          this.cancelCollect(params.index, params.row);
                          event.currentTarget.className ==
                            "ivu-icon ivu-icon-ios-star-outline";
                        } else {
                          this.collect(params.index, params.row);
                          event.currentTarget.className =
                            "ivu-icon ivu-icon-ios-star";
                        }
                      } else {
                        this.$Message.warning("请先登录");
                      }
                    }
                  }
                }),
                h("span", params.row.coin)
              ]);
            }
          },
          {
            title: this.$t("exchange.lastprice")+"/"+this.$t("swap.daychange"),
            key: "lastprice",
            sortable: true,
            sortMethod: function(a, b, type) {
              let a1 = parseFloat(a);
              let b1 = parseFloat(b);
              if (type == "asc") {
                return a1 - b1;
              } else {
                return b1 - a1;
              }
            }
          }
        ]
      },
      wallet: {
        base: 0.0,
        coin: 0.0
      },
      closePostion: false,
      operatePrice: "limitedPrice",

      trade: {
        rows: [],
        columns: [
          {
            title: self.$t("swap.price"),
            key: "price",
            render: (h, params) => {
              const row = params.row;
              const className = row.direction == "BUY" ? "buy" : "sell";

              return h(
                "span",
                {
                  attrs: {
                    class: className
                  }
                },
                params.row.price.toFixed(this.baseCoinScale)
              );
            },
            renderHeader: (h, params) => {
              const title =
                self.$t("swap.price") + "(" + self.currentCoin.base + ")";
              return h("span", {}, title);
            }
          },
          {
            title: self.$t("swap.num"),
            key: "amount",
            render: (h, params) => {
              return h("span", {}, params.row.amount.toFixed(this.coinScale));
            },
            renderHeader: (h, params) => {
              const title =
                self.$t("swap.num") + "(" + self.currentCoin.coin + ")";
              return h("span", {}, title);
            }
          },
          {
            title: self.$t("swap.time"),
            key: "time",
            render: (h, params) => {
              return h("span", {}, this.timeFormat(params.row.time));
            }
          }
        ]
      },
      //   最新价的 table 数据;
      plate: {
        maxPostion: 10,
        columns: [
          {
            //   价格;
            title: self.$t("swap.price"),
            key: "price",
            render: (h, params) => {
              let str = "";
              let price = "";
              const className = params.row.direction.toLowerCase();
              params.row.price == 0 && (str = h("span", {}, "--"));
              params.row.price != 0 &&
                (price = params.row.price.toFixed(this.baseCoinScale)) &&
                (str = h(
                  "span",
                  {
                    attrs: {
                      class: className
                    }
                  },
                  price
                ));
              return str;
            },
            renderHeader: (h, params) => {
              const title =
                self.$t("swap.price") + "(" + self.currentCoin.base + ")";
              return h("span", {}, title);
            }
          },
          {
            title: self.$t("swap.num"),
            key: "amount",
            render: (h, params) => {
              let str = "";
              params.row.amount == 0 && (str = h("span", {}, "--"));
              params.row.amount != 0 &&
                (str = h(
                  "span",
                  {},
                  params.row.amount.toFixed(this.coinScale)
                ));
              return str;
            },
            renderHeader: (h, params) => {
              const title =
                self.$t("swap.num") + "(" + self.currentCoin.coin + ")";
              return h("span", {}, title);
            }
          },
          {
            title: self.$t("swap.total"),
            key: "totalAmount",
            render: (h, params) => {
              if (params.row.price == 0 || params.row.totalAmount == 0) {
                return h("span", {}, "--");
              } else {
                return h(
                  "span",
                  {},
                  params.row.totalAmount.toFixed(this.coinScale)
                );
              }
            },
            renderHeader: (h, params) => {
              const title =
                self.$t("swap.total") + "(" + self.currentCoin.coin + ")";
              return h("span", {}, title);
            }
          },
          {
            className: "percenttd",
            width: 1,
            render: (h, params) => {
              let width = "0",
                backgroundColor =
                  params.row.direction === "BUY" ? "#00b275" : "#f15057",
                totle =
                  params.row.direction === "BUY"
                    ? this.plate.bidTotle
                    : this.plate.askTotle;
              if (params.row.totalAmount) {
                width = (params.row.totalAmount / totle).toFixed(4) * 100 + "%";
              }
              return h(
                "div",
                {
                  style: {
                    width,
                    backgroundColor
                  },
                  attrs: {
                    class: "percentdiv"
                  }
                },
                " "
              );
            }
          }
        ],
        askRows: [],
        bidRows: []
      },

      currentPosition: {
        columns: [
          {
            type: "index",
            width: 40,
            height:40,
            render: (h, params) => {
              return h(expandRow, {
                props: {
                  skin: params.row.skin,
                  rows: params.row.detail
                }
              });
            }
          },
          {
            title: self.$t("swap.time"),
            key: "time",
            render: (h, params) => {
              return h("span", {}, this.dateFormat(params.row.time));
            }
          },
          {
            title: self.$t("swap.symbol"),
            key: "symbol"
          },
          {
            title: self.$t("swap.type"),
            render(h, params) {
              return h(
                "span",
                params.row.type === "LIMIT_PRICE" ? "限价" : "市价"
              );
            }
          },
          {
            title: self.$t("swap.direction"),
            key: "direction",
            render: (h, params) => {
              const row = params.row;
              const className = row.direction.toLowerCase();
              return h(
                "span",
                {
                  attrs: {
                    class: className
                  }
                },
                row.direction == "BUY"
                  ? self.$t("swap.buyin")
                  : self.$t("swap.sellout")
              );
            }
          },
          {
            title: self.$t("swap.price"),
            key: "price",
            render(h, params) {
              return h("span", self.toFloor(params.row.price));
            }
          },
          {
            title: self.$t("swap.num"),
            key: "amount",
            render(h, params) {
              return h("span", self.toFloor(params.row.amount));
            }
          },
          {
            title: self.$t("swap.traded"),
            key: "tradedAmount",
            render(h, params) {
              return h("span", self.toFloor(params.row.tradedAmount));
            }
          },
          {
            title: self.$t("swap.dealamount"),
            key: "turnover",
            render(h, params) {
              return h("span", self.toFloor(params.row.turnover));
            }
          },
          {
            title: self.$t("swap.action"),
            key: "operate",
            width: 110,
            render: (h, params) => {
              return h(
                "Button",
                {
                  props: {
                    size: "small"
                  },
                  style: {
                    border: "1px solid #f0ac19",
                    color: "#f1ac19",
                    "line-height": "1.2",
                    "border-radius": "10px"
                  },
                  on: {
                    click: () => {
                      // console.log("======开始撤单")
                      this.cancel(params.index);
                    }
                  }
                },
                self.$t("swap.undo")
              );
            }
          }
        ],
        rows: []
      },

      currentOrder: {
        columns: [
          {
            type: "index",
            width: 40,
            height:40,
            render: (h, params) => {
              return h(expandRow, {
                props: {
                  skin: params.row.skin,
                  rows: params.row.detail
                }
              });
            }
          },
          {
            title: self.$t("swap.time"),
            key: "time",
            render: (h, params) => {
              return h("span", {}, this.dateFormat(params.row.time));
            }
          },
          {
            title: self.$t("swap.symbol"),
            key: "symbol"
          },
          {
            title: self.$t("swap.type"),
            render(h, params) {
              return h(
                "span",
                params.row.type === "LIMIT_PRICE" ? "限价" : "市价"
              );
            }
          },
          {
            title: self.$t("swap.direction"),
            key: "direction",
            render: (h, params) => {
              const row = params.row;
              const className = row.direction.toLowerCase();
              return h(
                "span",
                {
                  attrs: {
                    class: className
                  }
                },
                row.direction == "BUY"
                  ? self.$t("swap.buyin")
                  : self.$t("swap.sellout")
              );
            }
          },
          {
            title: self.$t("swap.price"),
            key: "price",
            render(h, params) {
              return h("span", self.toFloor(params.row.price));
            }
          },
          {
            title: self.$t("swap.num"),
            key: "amount",
            render(h, params) {
              return h("span", self.toFloor(params.row.amount));
            }
          },
          {
            title: self.$t("swap.traded"),
            key: "tradedAmount",
            render(h, params) {
              return h("span", self.toFloor(params.row.tradedAmount));
            }
          },
          {
            title: self.$t("swap.dealamount"),
            key: "turnover",
            render(h, params) {
              return h("span", self.toFloor(params.row.turnover));
            }
          },
          {
            title: self.$t("swap.action"),
            key: "operate",
            width: 110,
            render: (h, params) => {
              return h(
                "Button",
                {
                  props: {
                    size: "small"
                  },
                  style: {
                    border: "1px solid #f0ac19",
                    color: "#f1ac19",
                    "line-height": "1.2",
                    "border-radius": "10px"
                  },
                  on: {
                    click: () => {
                      // console.log("======开始撤单")
                      this.cancel(params.index);
                    }
                  }
                },
                self.$t("swap.undo")
              );
            }
          }
        ],
        rows: []
      },
      historyOrder: {
        pageSize: 10,
        total: 10,
        page: 0,
        columns: [
          {
            type: "index",
            width: 40,
            render: (h, params) => {
              return h(expandRow, {
                props: {
                  skin: params.row.skin,
                  rows: params.row.detail
                }
              });
            }
          },
          {
            title: self.$t("swap.time"),
            key: "time",
            render: (h, params) => {
              return h("span", {}, this.dateFormat(params.row.time));
            }
          },
          {
            title: self.$t("swap.symbol"),
            key: "symbol"
          },
          {
            title: self.$t("swap.type"),
            render(h, params) {
              return h(
                "span",
                params.row.type === "LIMIT_PRICE" ? "限价" : "市价"
              );
            }
          },
          {
            title: self.$t("swap.direction"),
            key: "direction",
            render: (h, params) => {
              const row = params.row;
              const className = row.direction.toLowerCase();
              return h(
                "span",
                {
                  attrs: {
                    class: className
                  }
                },
                row.direction == "BUY"
                  ? self.$t("swap.buyin")
                  : self.$t("swap.sellout")
              );
            }
          },
          {
            title: self.$t("swap.price"),
            key: "price",
            render(h, params) {
              return h("span", self.toFloor(params.row.price));
            }
          },
          {
            title: self.$t("swap.num"),
            key: "amount",
            render(h, params) {
              return h("span", self.toFloor(params.row.amount));
            }
          },
          {
            title: self.$t("swap.done"),
            key: "tradedAmount",
            render(h, params) {
              return h("span", self.toFloor(params.row.tradedAmount));
            }
          },
          {
            title: self.$t("swap.dealamount"),
            key: "turnover",
            render(h, params) {
              return h("span", self.toFloor(params.row.turnover));
            }
          },
          {
            title: self.$t("swap.status"),
            key: "status",
            render: (h, params) => {
              const status = params.row.status;
              if (status == "COMPLETED") {
                return h(
                  "span",
                  {
                    style: {
                      color: "#f0a70a"
                    }
                  },
                  self.$t("swap.finished")
                );
              } else if (status == "CANCELED") {
                return h(
                  "span",
                  {
                    style: {
                      color: "#7c7f82"
                    }
                  },
                  self.$t("swap.canceled")
                );
              } else {
                return h("span", {}, "--");
              }
            }
          }
        ],
        rows: []
      },
    }
  },

  filters: {

  },
  computed: {

  },

  watch: {

  },

  created: function() {
    this.init();
  },
  
  // functions that mutate state and trigger updates
  methods: {


    init() {
      var params = this.$route.params.pair;
      if (params == undefined) {
        this.$router.push("/swap/" + this.defaultPath);
        params = this.defaultPath;
      }
      const basecion = params.split("_")[1];
      if (basecion) {
        this.basecion = basecion;
      }
      var coin = params.toUpperCase().split("_")[0];
      var base = params.toUpperCase().split("_")[1];

      this.$store.commit("navigate", "nav-swap");
      this.$store.commit("setSkin", this.skin);
    },

    changePlate(str) {
      if (str != "all") {
        this.plate.maxPostion = 20;
      } else {
        this.plate.maxPostion = 10;
      }
      this.getPlate(str);
      //this.selectedPlate = str;
    },


    changeImgTable(str) {
      this.currentImgTable = str;
    },
    changeOrder(str) {
      this.selectedOrder = str;
    },
    changeOperatePrice(str) {
      this.operatePrice = str;
    },

    getPlate(str="") {
      //买卖盘
      var params = {};
      params["symbol"] = this.currentCoin.symbol;
      this.$http
        .post(this.host + this.api.market.platemini, params)
        .then(response => {
          this.plate.askRows = [];
          this.plate.bidRows = [];
          let resp = response.body;
          if (resp.ask && resp.ask.items) {
            for (var i = 0; i < resp.ask.items.length; i++) {
              if (i == 0) {
                resp.ask.items[i].totalAmount = resp.ask.items[i].amount;
              } else {
                resp.ask.items[i].totalAmount =
                  resp.ask.items[i - 1].totalAmount + resp.ask.items[i].amount;
              }
            }
            if (resp.ask.items.length >= this.plate.maxPostion) {
              for (var i = this.plate.maxPostion; i > 0; i--) {
                var ask = resp.ask.items[i - 1];
                ask.direction = "SELL";
                ask.position = i;
                this.plate.askRows.push(ask);
              }
              const rows = this.plate.askRows,
                len = rows.length,
                totle = rows[0].totalAmount;
              this.plate.askTotle = totle;
            } else {
              for (var i = this.plate.maxPostion; i > resp.ask.items.length; i--) {
                var ask = { price: 0, amount: 0 };
                ask.direction = "SELL";
                ask.position = i;
                ask.totalAmount = ask.amount;
                this.plate.askRows.push(ask);
              }
              for (var i = resp.ask.items.length; i > 0; i--) {
                var ask = resp.ask.items[i - 1];
                ask.direction = "SELL";
                ask.position = i;
                this.plate.askRows.push(ask);
              }
              var askItemIndex = (resp.ask.items.length - 1) > 0 ? (resp.ask.items.length - 1) : 0;
              const rows = this.plate.askRows,
                len = rows.length,
                totle =
                  rows[askItemIndex]
                    .totalAmount;
              this.plate.askTotle = totle;
            }
          }
          if (resp.bid && resp.bid.items) {
            for (var i = 0; i < resp.bid.items.length; i++) {
              if (i == 0)
                resp.bid.items[i].totalAmount = resp.bid.items[i].amount;
              else
                resp.bid.items[i].totalAmount =
                  resp.bid.items[i - 1].totalAmount + resp.bid.items[i].amount;
            }
            for (var i = 0; i < resp.bid.items.length; i++) {
              var bid = resp.bid.items[i];
              bid.direction = "BUY";
              bid.position = i + 1;
              this.plate.bidRows.push(bid);
              if (i == this.plate.maxPostion - 1) break;
            }
            if (resp.bid.items.length < this.plate.maxPostion) {
              for (
                var i = resp.bid.items.length;
                i < this.plate.maxPostion;
                i++
              ) {
                var bid = { price: 0, amount: 0 };
                bid.direction = "BUY";
                bid.position = i + 1;
                bid.totalAmount = 0;
                this.plate.bidRows.push(bid);
              }
              var bidItemIndex = (resp.bid.items.length - 1) > 0 ? (resp.bid.items.length - 1) : 0;
              const rows = this.plate.bidRows,
                len = rows.length,
                totle = rows[bidItemIndex].totalAmount;
              this.plate.bidTotle = totle;
            } else {
              const rows = this.plate.bidRows,
                len = rows.length,
                totle = rows[len - 1].totalAmount;
              this.plate.bidTotle = totle;
            }
          }
          if(str!=""){
            this.selectedPlate = str;
          }
        });
    },

    startWebsock() {
      if (this.stompClient) {
        this.stompClient.ws.close();
      }
      var stompClient = null;
      var that = this;
      var socket = new SockJS(that.host + that.api.market.ws);
      stompClient = Stomp.over(socket);
      this.stompClient = stompClient;
      stompClient.debug = false;
      // this.datafeed = new Datafeeds.WebsockFeed(that.host+'/market',this.currentCoin,stompClient);
      // this.getKline();
      stompClient.connect({}, function(frame) {
        that.datafeed = new Datafeeds.WebsockFeed(
          that.host + "/market",
          that.currentCoin,
          stompClient,
          that.baseCoinScale
        );
        that.getKline();
        //订阅价格变化消息
        stompClient.subscribe("/topic/market/thumb", function(msg) {
          var resp = JSON.parse(msg.body);
          var coin = that.getCoin(resp.symbol);
          if (coin != null) {
            // coin.price = resp.close.toFixed(2);
            coin.price = resp.close;
            coin.rose =
              resp.chg > 0
                ? "+" + (resp.chg * 100).toFixed(2) + "%"
                : (resp.chg * 100).toFixed(2) + "%";
            // coin.close = resp.close.toFixed(2);
            // coin.high = resp.high.toFixed(2);
            // coin.low = resp.low.toFixed(2);
            coin.close = resp.close;
            coin.high = resp.high;
            coin.low = resp.low;
            coin.turnover = parseInt(resp.volume);
            coin.volume = resp.volume;
            coin.usdRate = resp.usdRate;
          }
        });
        //订阅实时成交信息
        stompClient.subscribe(
          "/topic/market/trade/" + that.currentCoin.symbol,
          function(msg) {
            var resp = JSON.parse(msg.body);
            if (resp.length > 0) {
              for (var i = 0; i < resp.length; i++) {
                that.trade.rows.unshift(resp[i]);
              }
            }
            if (that.trade.rows.length > 30) {
              that.trade.rows = that.trade.rows.slice(0, 30);
            }
          }
        );
        if (that.isLogin) {
          //订阅委托取消信息
          stompClient.subscribe(
            "/topic/market/order-canceled/" +
              that.currentCoin.symbol +
              "/" +
              that.member.id,
            function(msg) {
              var resp = JSON.parse(msg.body);
              that.refreshAccount();
            }
          );
          //订阅委托交易完成
          stompClient.subscribe(
            "/topic/market/order-completed/" +
              that.currentCoin.symbol +
              "/" +
              that.member.id,
            function(msg) {
              var resp = JSON.parse(msg.body);
              that.refreshAccount();
            }
          );
          //订阅委托部分交易
          stompClient.subscribe(
            "/topic/market/order-trade/" +
              that.currentCoin.symbol +
              "/" +
              that.member.id,
            function(msg) {
              var resp = JSON.parse(msg.body);
              that.refreshAccount();
            }
          );
        }

        //订阅盘口消息
        stompClient.subscribe(
          "/topic/market/trade-plate/" + that.currentCoin.symbol,
          function(msg) {
            var resp = JSON.parse(msg.body);
            if (resp.direction == "SELL") {
              var asks = resp.items;
              that.plate.askRows = [];
              let totle = 0;
              for (var i = that.plate.maxPostion - 1; i >= 0; i--) {
                var ask = {};
                if (i < asks.length) {
                  ask = asks[i];
                } else {
                  ask["price"] = 0;
                  ask["amount"] = 0;
                }
                ask.direction = "SELL";
                ask.position = i + 1;
                that.plate.askRows.push(ask);
              }
              for (var i = that.plate.askRows.length - 1; i >= 0; i--) {
                if (
                  i == that.plate.askRows.length - 1 ||
                  that.plate.askRows[i].price == 0
                ) {
                  that.plate.askRows[i].totalAmount =
                    that.plate.askRows[i].amount;
                } else {
                  that.plate.askRows[i].totalAmount =
                    that.plate.askRows[i + 1].totalAmount +
                    that.plate.askRows[i].amount;
                }
                totle += that.plate.askRows[i].amount;
              }
              that.plate.askTotle = totle;
            } else {
              var bids = resp.items;
              that.plate.bidRows = [];
              let totle = 0;
              for (var i = 0; i < that.plate.maxPostion; i++) {
                var bid = {};
                if (i < bids.length) {
                  bid = bids[i];
                } else {
                  bid["price"] = 0;
                  bid["amount"] = 0;
                }
                bid.direction = "BUY";
                bid.position = i + 1;
                that.plate.bidRows.push(bid);
              }
              for (var i = 0; i < that.plate.bidRows.length; i++) {
                if (i == 0 || that.plate.bidRows[i].amount == 0) {
                  that.plate.bidRows[i].totalAmount =
                    that.plate.bidRows[i].amount;
                } else {
                  that.plate.bidRows[i].totalAmount =
                    that.plate.bidRows[i - 1].totalAmount +
                    that.plate.bidRows[i].amount;
                }
                totle += that.plate.bidRows[i].amount;
              }
              that.plate.bidTotle = totle;
            }
            if(that.currentImgTable == 's') {
              that.getPlateFull();
            }
          }
        );
      });
    },


    getKline() {
      var that = this;
      let config = {
        autosize: true,
        height: 320,
        fullscreen: true,
        symbol: that.symbol,
        interval: "60", // 默认K线周期
        timezone: "Asia/Shanghai",
        toolbar_bg: "#18202a",
        container_id: "kline_container",
        datafeed: that.datafeed,
        library_path:
          process.env.NODE_ENV === "production"
            ? "/assets/charting_library/"
            : "src/assets/js/charting_library/",
        locale: "zh",
        debug: false,
        drawings_access: {
          type: "black",
          tools: [{ name: "Regression Trend" }]
        },
        disabled_features: [
          "header_resolutions",
          "timeframes_toolbar",
          "header_symbol_search",
          "header_chart_type",
          "header_compare",
          "header_undo_redo",
          "header_screenshot",
          "header_saveload",
          "use_localstorage_for_settings",
          "left_toolbar",
          "volume_force_overlay"
        ],
        enabled_features: [
          "hide_last_na_study_output",
          "move_logo_to_main_pane"
        ],
        custom_css_url: "bundles/common.css",
        supported_resolutions: ["1", "5", "15", "30", "60", "1D", "1W", "1M"],
        charts_storage_url: "http://saveload.tradingview.com",
        charts_storage_api_version: "1.1",
        client_id: "tradingview.com",
        user_id: "public_user_id",
        overrides: {
          "paneProperties.background": "#1B1E2E",
          "paneProperties.vertGridProperties.color": "rgba(0,0,0,.1)",
          "paneProperties.horzGridProperties.color": "rgba(0,0,0,.1)",
          //"scalesProperties.textColor" : "#AAA",
          "scalesProperties.textColor": "#61688A",
          "mainSeriesProperties.candleStyle.upColor": "#00b275",
          "mainSeriesProperties.candleStyle.downColor": "#f15057",
          "mainSeriesProperties.candleStyle.drawBorder": false,
          "mainSeriesProperties.candleStyle.wickUpColor": "#589065",
          "mainSeriesProperties.candleStyle.wickDownColor": "#AE4E54",
          "paneProperties.legendProperties.showLegend": false,

          "mainSeriesProperties.areaStyle.color1": "rgba(71, 78, 112, 0.5)",
          "mainSeriesProperties.areaStyle.color2": "rgba(71, 78, 112, 0.5)",
          "mainSeriesProperties.areaStyle.linecolor": "#9194a4",
          "volumePaneSize": "small"
        },
        time_frames: [
          {
            text: "1min",
            resolution: "1",
            description: "realtime",
            title: that.$t("exchange.realtime")
          },
          { text: "1min", resolution: "1", description: "1min" },
          { text: "5min", resolution: "5", description: "5min" },
          { text: "15min", resolution: "15", description: "15min" },
          { text: "30min", resolution: "30", description: "30min" },
          {
            text: "1hour",
            resolution: "60",
            description: "1hour",
            title: "1hour"
          },
          /*{ text: "4hour", resolution: "240", description: "4hour",title: "4hour" },*/
          {
            text: "1day",
            resolution: "1D",
            description: "1day",
            title: "1day"
          },
          {
            text: "1week",
            resolution: "1W",
            description: "1week",
            title: "1week"
          },
          { text: "1mon", resolution: "1M", description: "1mon" }
        ]
      };
      if (that.skin === "day") {
        config.toolbar_bg = "#fff";
        config.custom_css_url = "bundles/common_day.css";
        config.overrides["paneProperties.background"] = "#fff";
        config.overrides["mainSeriesProperties.candleStyle.upColor"] =
          "#a6d3a5";
        config.overrides["mainSeriesProperties.candleStyle.downColor"] =
          "#ffa5a6";
      }
      require(["@js/charting_library/charting_library.min.js"], function(tv) {
        var widget = (window.tvWidget = new TradingView.widget(config));
        widget.onChartReady(function() {
          widget.chart().executeActionById("drawingToolbarAction");
          widget
            .chart()
            .createStudy("Moving Average", false, false, [5], null, {
              "plot.color": "#EDEDED"
            });
          widget
            .chart()
            .createStudy("Moving Average", false, false, [10], null, {
              "plot.color": "#ffe000"
            });
          widget
            .chart()
            .createStudy("Moving Average", false, false, [30], null, {
              "plot.color": "#ce00ff"
            });
          widget
            .chart()
            .createStudy("Moving Average", false, false, [60], null, {
              "plot.color": "#00adff"
            });
          widget
            .createButton()
            .attr("title", "realtime")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(3);
              widget.setSymbol("", "1");
            })
            .append("<span>分时</span>");

          widget
            .createButton()
            .attr("title", "M1")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "1");
            })
            .append("<span>M1</span>");

          widget
            .createButton()
            .attr("title", "M5")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "5");
            })
            .append("<span>M5</span>");

          widget
            .createButton()
            .attr("title", "M15")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "15");
            })
            .append("<span>M15</span>");

          widget
            .createButton()
            .attr("title", "M30")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "30");
            })
            .append("<span>M30</span>");

          widget
            .createButton()
            .attr("title", "H1")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "60");
            })
            .append("<span>H1</span>")
            .addClass("selected");

          widget
            .createButton()
            .attr("title", "D1")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "1D");
            })
            .append("<span>D1</span>");

          widget
            .createButton()
            .attr("title", "W1")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "1W");
            })
            .append("<span>W1</span>");

          widget
            .createButton()
            .attr("title", "M1")
            .on("click", function() {
              if ($(this).hasClass("selected")) return;
              $(this)
                .addClass("selected")
                .parent(".group")
                .siblings(".group")
                .find(".button.selected")
                .removeClass("selected");
              widget.chart().setChartType(1);
              widget.setSymbol("", "1M");
            })
            .append("<span>M1</span>");
        });
      });
    },

    open_position() {
      this.closePostion = false;
    },
    close_position() {
      this.closePostion = true;
    },


    gohref(currentRow, oldCurrentRow) {
      this.$router.push({
          name: 'SwapPair',
          params: {
            pair: currentRow.href
          }
        });
    },


    buyPlate(currentRow) {
      this.form.buy.limitPrice = currentRow.price;
      this.form.sell.limitPrice = currentRow.price;
    },
    sellPlate(currentRow) {
      this.form.buy.limitPrice = currentRow.price;
      this.form.sell.limitPrice = currentRow.price;
    },


    keyEvent(event) {

    },
  },
}
</script>

