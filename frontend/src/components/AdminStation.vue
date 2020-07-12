<template>
  <div class="flex justify-center md:py-10 lg:py-10 app-container">
    <div class="max-w-md width-450px rounded bg-white shadow-lg p-6">
      <div class="font-bold text-xl mb-3 text-center">지하철역 관리</div>
      <form>
        <div class="mb-4">
          <input
            v-model="stationName"
            class="appearance-none border rounded py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline w-full"
            id="station-name"
            type="text"
            autocomplete="off"
            placeholder="역 이름을 추가해주세요."
          />
          <button
            v-on:click="addStation"
            id="station-add-btn"
            class="inline-block text-sm bg-yellow-500 hover:bg-yellow-400 hover:text-gray-700 text-gray-800 font-bold py-2 px-4 rounded"
          >추가</button>
        </div>
      </form>
      <div class="mt-5 flex station-list-container relative overflow-y-auto">
        <div id="station-list" class="w-full">
          <div
            v-for="value in stations"
            v-bind:key="value.id"
            class="list-item border border-gray-200 py-2 px-4 text-gray-800"
            :data-id="value.id"
          >
            {{value.name}}
            <button
              v-on:click="deleteStation(value)"
              class="hover:bg-gray-300 hover:text-gray-500 text-gray-300 px-1 rounded-full float-right"
            >
              <span class="mdi mdi-delete"></span>
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  CONFIRM_MESSAGE,
  ERROR_MESSAGE
} from "../../public/utils/constants.js";
import api from "../../public/api";
export default {
  data() {
    return {
      stationName: null,
      stations: []
    };
  },
  mounted() {
    this.getStations();
  },
  methods: {
    getStations() {
      api.station.get().then(data => {
        this.stations = data;
      });
    },
    addStation() {
      const regExp = new RegExp(/^[^\d\s]+$/);
      const station = {
        name: this.stationName
      };
      if (event) event.preventDefault();
      if (!this.stationName) {
        alert(ERROR_MESSAGE.NOT_EMPTY);
        return;
      }

      if (this.validateReduplication()) {
        alert(ERROR_MESSAGE.DUPLICATE_STATION_NAME);
        this.stationName = null;
        return;
      }

      if (!regExp.test(this.stationName)) {
        alert(ERROR_MESSAGE.INCORRECT_STATION_NAME);
        this.stationName = null;
        return;
      }
      api.station.create(station).then(data => {
        this.stations.push(data);
      });
      this.stationName = null;
    },
    validateReduplication() {
      let isReduplicate = false;

      this.stations.forEach(item => {
        if (item.name === this.stationName) {
          isReduplicate = true;
          return;
        }
      });

      return isReduplicate;
    },
    deleteStation(value) {
      const isRemove = confirm(CONFIRM_MESSAGE.REMOVE);

      if (!isRemove) {
        return;
      }

      api.station.delete(value.id).then(() => {
        this.stations.splice(this.stations.indexOf(value), 1);
      });
    }
  }
};
</script>

<style>
@import "../../public/css/admin-station.css";
</style>
