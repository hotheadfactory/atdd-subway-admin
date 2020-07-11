<template>
  <div>
    <div class="flex justify-center md:py-10 lg:py-10 app-container">
      <div class="max-w-md width-450px rounded bg-white shadow-lg p-6">
        <div>
          <div
            class="font-bold text-xl mb-4 relative w-full flex items-stretch"
          >
            <div class="flex-1"></div>
            <div class="flex-1 text-center">노선 관리</div>
            <div class="flex-1 flex justify-end">
              <button
                v-on:click="showModal"
                id="subway-line-add-btn"
                class="modal-open bg-transparent bg-yellow-500 hover:bg-yellow-400 hover:text-gray-700 text-gray-800 text-sm px-3 py-1 rounded"
              >
                노선 추가
              </button>
            </div>
          </div>
          <div class="lines-info flex flex-wrap mb-3 w-full">
            <div class="w-1/2 p-2 text-center text-gray-800 bg-gray-200">
              첫차 시간
            </div>
            <div
              class="w-1/2 p-2 text-center text-gray-800 bg-gray-100"
              id="start-time-view"
            >
              --:--
            </div>
            <div class="w-1/2 p-2 text-center text-gray-800 bg-gray-200">
              막차 시간
            </div>
            <div
              class="w-1/2 p-2 text-center text-gray-800 bg-gray-100"
              id="end-time-view"
            >
              --:--
            </div>
            <div class="w-1/2 p-2 text-center text-gray-800 bg-gray-200">
              간격
            </div>
            <div
              class="w-1/2 p-2 text-center text-gray-800 bg-gray-100"
              id="interval-time-view"
            >
              -
            </div>
          </div>
        </div>
        <div class="mt-5 flex subway-lines-container relative overflow-y-auto">
          <div id="subway-line-list" class="w-full">
            <div
              v-for="line in lines"
              v-bind:key="line.id"
              class="subway-line-item border border-gray-200 py-2 px-4 text-gray-800"
              :data-id="line.id"
            >
              <span
                class="w-3 h-3 rounded-full inline-block mr-1"
                :class="line.color"
              ></span>
              <span id="line-name">{{ line.name }}</span>
              <button
                class="hover:bg-gray-300 hover:text-gray-500 text-gray-300 px-1 rounded-full float-right"
              >
                <span class="mdi mdi-delete"></span>
              </button>
              <button
                class="hover:bg-gray-300 hover:text-gray-500 text-gray-300 px-1 rounded-full float-right"
              >
                <span class="mdi mdi-pencil"></span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      v-if="modalVisible"
      class="modal modal-active fixed w-full h-full top-0 left-0 flex items-center justify-center"
    >
      <div
        class="modal-overlay absolute w-full h-full bg-gray-900 opacity-50"
      ></div>
      <div
        class="modal-container bg-white w-11/12 md:max-w-md mx-auto rounded shadow-lg z-50 overflow-y-auto"
      >
        <div class="modal-content text-left px-8 py-4">
          <div class="flex justify-center items-center pb-3 pt-3">
            <p class="text-2xl font-bold">노선 정보</p>
          </div>
          <div id="subway-line-create-form" class="mb-4">
            <div class="mb-4">
              <label
                class="block text-gray-700 text-sm font-bold mb-2"
                for="subway-line-name"
                >노선 이름</label
              >
              <input
                v-model="subwayLineName"
                class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="subway-line-name"
                type="text"
                placeholder="노선 이름을 입력해주세요"
              />
            </div>

            <div class="flex mb-4">
              <div class="w-1/3 pr-2">
                <label
                  class="block text-gray-700 text-sm font-bold mb-2"
                  for="first-time"
                  >첫차 시간</label
                >
                <input
                  v-model="firstTime"
                  class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="first-time"
                  type="text"
                  placeholder="첫차 시간"
                />
              </div>
              <div class="w-1/3 pr-2">
                <label
                  class="block text-gray-700 text-sm font-bold mb-2"
                  for="last-time"
                  >막차 시간</label
                >
                <input
                  v-model="lastTime"
                  class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="last-time"
                  type="text"
                  placeholder="막차 시간"
                />
              </div>

              <div class="w-1/3">
                <label
                  class="block text-gray-700 text-sm font-bold mb-2"
                  for="interval-time"
                  >간격</label
                >
                <input
                  v-model="intervalTime"
                  class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                  id="interval-time"
                  type="number"
                  placeholder="간격"
                />
              </div>
            </div>

            <div id="subway-line-color-select-container" class="mb-4">
              <label
                class="block text-gray-700 text-sm font-bold mb-2"
                for="subway-line-color"
                >노선 색상</label
              >
              <input
                v-model="color"
                placeholder="색상을 선택해주세요"
                class="bg-gray-200 mb-4 appearance-none border-2 border-gray-200 rounded w-full py-2 px-4 text-gray-700 leading-tight focus:outline-none focus:bg-white focus:border-purple-500"
                id="subway-line-color"
                type="text"
                disabled
              />
              <button
                v-for="data in colorOptions"
                v-on:click="inputColor(data.bgColor)"
                v-bind:key="data.color"
                :data-color="data.color"
                :class="[data.bgColor, data.hoverColor]"
                class="color-select-option button w-6 h-6 font-bold p-1 rounded"
              ></button>
            </div>

            <div class="flex justify-end pt-4">
              <button
                v-on:click="closeModal"
                class="modal-close px-4 bg-transparent p-3 rounded text-gray-600 hover:bg-gray-100 hover:text-gray-700 mr-2 text-sm"
              >
                취소
              </button>
              <button
                v-on:click="addLine"
                class="px-4 bg-yellow-500 hover:bg-yellow-400 hover:text-gray-700 text-gray-800 rounded text-white text-sm"
              >
                확인
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "../../public/api";

const subwayLineColorOptions = [
  {
    color: "gray-300",
    bgColor: "bg-gray-300",
    hoverColor: "hover:bg-gray-200",
  },
  {
    color: "gray-400",
    bgColor: "bg-gray-400",
    hoverColor: "hover:bg-gray-300",
  },
  {
    color: "gray-500",
    bgColor: "bg-gray-500",
    hoverColor: "hover:bg-gray-400",
  },
  {
    color: "gray-600",
    bgColor: "bg-gray-600",
    hoverColor: "hover:bg-gray-500",
  },
  {
    color: "gray-700",
    bgColor: "bg-gray-700",
    hoverColor: "hover:bg-gray-600",
  },
  {
    color: "gray-800",
    bgColor: "bg-gray-800",
    hoverColor: "hover:bg-gray-700",
  },
  {
    color: "gray-900",
    bgColor: "bg-gray-900",
    hoverColor: "hover:bg-gray-800",
  },
  {
    color: "red-300",
    bgColor: "bg-red-300",
    hoverColor: "hover:bg-red-200",
  },
  {
    color: "red-400",
    bgColor: "bg-red-400",
    hoverColor: "hover:bg-red-300",
  },
  {
    color: "red-500",
    bgColor: "bg-red-500",
    hoverColor: "hover:bg-red-400",
  },
  {
    color: "red-600",
    bgColor: "bg-red-600",
    hoverColor: "hover:bg-red-500",
  },
  {
    color: "red-700",
    bgColor: "bg-red-700",
    hoverColor: "hover:bg-red-600",
  },
  {
    color: "red-800",
    bgColor: "bg-red-800",
    hoverColor: "hover:bg-red-700",
  },
  {
    color: "red-900",
    bgColor: "bg-red-900",
    hoverColor: "hover:bg-red-800",
  },
  {
    color: "orange-300",
    bgColor: "bg-orange-300",
    hoverColor: "hover:bg-orange-200",
  },
  {
    color: "orange-400",
    bgColor: "bg-orange-400",
    hoverColor: "hover:bg-orange-300",
  },
  {
    color: "orange-500",
    bgColor: "bg-orange-500",
    hoverColor: "hover:bg-orange-400",
  },
  {
    color: "orange-600",
    bgColor: "bg-orange-600",
    hoverColor: "hover:bg-orange-500",
  },
  {
    color: "orange-700",
    bgColor: "bg-orange-700",
    hoverColor: "hover:bg-orange-600",
  },
  {
    color: "orange-800",
    bgColor: "bg-orange-800",
    hoverColor: "hover:bg-orange-700",
  },
  {
    color: "orange-900",
    bgColor: "bg-orange-900",
    hoverColor: "hover:bg-orange-800",
  },
  {
    color: "yellow-300",
    bgColor: "bg-yellow-300",
    hoverColor: "hover:bg-yellow-200",
  },
  {
    color: "yellow-400",
    bgColor: "bg-yellow-400",
    hoverColor: "hover:bg-yellow-300",
  },
  {
    color: "yellow-500",
    bgColor: "bg-yellow-500",
    hoverColor: "hover:bg-yellow-400",
  },
  {
    color: "yellow-600",
    bgColor: "bg-yellow-600",
    hoverColor: "hover:bg-yellow-500",
  },
  {
    color: "yellow-700",
    bgColor: "bg-yellow-700",
    hoverColor: "hover:bg-yellow-600",
  },
  {
    color: "yellow-800",
    bgColor: "bg-yellow-800",
    hoverColor: "hover:bg-yellow-700",
  },
  {
    color: "yellow-900",
    bgColor: "bg-yellow-900",
    hoverColor: "hover:bg-yellow-800",
  },
  {
    color: "green-300",
    bgColor: "bg-green-300",
    hoverColor: "hover:bg-green-200",
  },
  {
    color: "green-400",
    bgColor: "bg-green-400",
    hoverColor: "hover:bg-green-300",
  },
  {
    color: "green-500",
    bgColor: "bg-green-500",
    hoverColor: "hover:bg-green-400",
  },
  {
    color: "green-600",
    bgColor: "bg-green-600",
    hoverColor: "hover:bg-green-500",
  },
  {
    color: "green-700",
    bgColor: "bg-green-700",
    hoverColor: "hover:bg-green-600",
  },
  {
    color: "green-800",
    bgColor: "bg-green-800",
    hoverColor: "hover:bg-green-700",
  },
  {
    color: "green-900",
    bgColor: "bg-green-900",
    hoverColor: "hover:bg-green-800",
  },
  {
    color: "teal-300",
    bgColor: "bg-teal-300",
    hoverColor: "hover:bg-teal-200",
  },
  {
    color: "teal-400",
    bgColor: "bg-teal-400",
    hoverColor: "hover:bg-teal-300",
  },
  {
    color: "teal-500",
    bgColor: "bg-teal-500",
    hoverColor: "hover:bg-teal-400",
  },
  {
    color: "teal-600",
    bgColor: "bg-teal-600",
    hoverColor: "hover:bg-teal-500",
  },
  {
    color: "teal-700",
    bgColor: "bg-teal-700",
    hoverColor: "hover:bg-teal-600",
  },
  {
    color: "teal-800",
    bgColor: "bg-teal-800",
    hoverColor: "hover:bg-teal-700",
  },
  {
    color: "teal-900",
    bgColor: "bg-teal-900",
    hoverColor: "hover:bg-teal-800",
  },
  {
    color: "blue-300",
    bgColor: "bg-blue-300",
    hoverColor: "hover:bg-blue-200",
  },
  {
    color: "blue-400",
    bgColor: "bg-blue-400",
    hoverColor: "hover:bg-blue-300",
  },
  {
    color: "blue-500",
    bgColor: "bg-blue-500",
    hoverColor: "hover:bg-blue-400",
  },
  {
    color: "blue-600",
    bgColor: "bg-blue-600",
    hoverColor: "hover:bg-blue-500",
  },
  {
    color: "blue-700",
    bgColor: "bg-blue-700",
    hoverColor: "hover:bg-blue-600",
  },
  {
    color: "blue-800",
    bgColor: "bg-blue-800",
    hoverColor: "hover:bg-blue-700",
  },
  {
    color: "blue-900",
    bgColor: "bg-blue-900",
    hoverColor: "hover:bg-blue-800",
  },
  {
    color: "indigo-300",
    bgColor: "bg-indigo-300",
    hoverColor: "hover:bg-indigo-200",
  },
  {
    color: "indigo-400",
    bgColor: "bg-indigo-400",
    hoverColor: "hover:bg-indigo-300",
  },
  {
    color: "indigo-500",
    bgColor: "bg-indigo-500",
    hoverColor: "hover:bg-indigo-400",
  },
  {
    color: "indigo-600",
    bgColor: "bg-indigo-600",
    hoverColor: "hover:bg-indigo-500",
  },
  {
    color: "indigo-700",
    bgColor: "bg-indigo-700",
    hoverColor: "hover:bg-indigo-600",
  },
  {
    color: "indigo-800",
    bgColor: "bg-indigo-800",
    hoverColor: "hover:bg-indigo-700",
  },
  {
    color: "indigo-900",
    bgColor: "bg-indigo-900",
    hoverColor: "hover:bg-indigo-800",
  },
  {
    color: "purple-300",
    bgColor: "bg-purple-300",
    hoverColor: "hover:bg-purple-200",
  },
  {
    color: "purple-400",
    bgColor: "bg-purple-400",
    hoverColor: "hover:bg-purple-300",
  },
  {
    color: "purple-500",
    bgColor: "bg-purple-500",
    hoverColor: "hover:bg-purple-400",
  },
  {
    color: "purple-600",
    bgColor: "bg-purple-600",
    hoverColor: "hover:bg-purple-500",
  },
  {
    color: "purple-700",
    bgColor: "bg-purple-700",
    hoverColor: "hover:bg-purple-600",
  },
  {
    color: "purple-800",
    bgColor: "bg-purple-800",
    hoverColor: "hover:bg-purple-700",
  },
  {
    color: "purple-900",
    bgColor: "bg-purple-900",
    hoverColor: "hover:bg-purple-800",
  },
  {
    color: "pink-300",
    bgColor: "bg-pink-300",
    hoverColor: "hover:bg-pink-200",
  },
  {
    color: "pink-400",
    bgColor: "bg-pink-400",
    hoverColor: "hover:bg-pink-300",
  },
  {
    color: "pink-500",
    bgColor: "bg-pink-500",
    hoverColor: "hover:bg-pink-400",
  },
  {
    color: "pink-600",
    bgColor: "bg-pink-600",
    hoverColor: "hover:bg-pink-500",
  },
  {
    color: "pink-700",
    bgColor: "bg-pink-700",
    hoverColor: "hover:bg-pink-600",
  },
  {
    color: "pink-800",
    bgColor: "bg-pink-800",
    hoverColor: "hover:bg-pink-700",
  },
  {
    color: "pink-900",
    bgColor: "bg-pink-900",
    hoverColor: "hover:bg-pink-800",
  },
];

export default {
  data() {
    return {
      colorOptions: subwayLineColorOptions,
      modalVisible: false,
      lines: [],
      color: null,
      subwayLineName: null,
      firstTime: null,
      lastTime: null,
      intervalTime: null,
    };
  },
  mounted() {
    this.getLines();
  },
  methods: {
    showModal() {
      if (event) event.preventDefault();
      this.modalVisible = true;
    },
    closeModal() {
      if (event) event.preventDefault();
      this.color = null;
      this.subwayLineName = null;
      this.firstTime = null;
      this.lastTime = null;
      this.intervalTime = null;
      this.modalVisible = false;
    },
    inputColor(color) {
      if (event) event.preventDefault();
      this.color = color;
    },
    getLines() {
      api.line.get().then((data) => {
        if (data.error) {
          alert(data.error);
          return;
        }
        this.lines = data;
      });
    },
    addLine() {
      const data = {
        name: this.subwayLineName,
        startTime: this.firstTime,
        endTime: this.lastTime,
        intervalTime: this.intervalTime,
        color: this.color,
      };

      api.line.create(data).then((data) => {
        if (data.error) {
          alert(data.error);
          return;
        }
        console.log(data);
        this.lines.push(data);
        this.closeModal();
      });
    },
  },
};
</script>

<style>
.subway-lines-container {
  max-height: calc(100vh - 450px);
}

.lines-list {
  height: calc(100% - 400px);
}
</style>
