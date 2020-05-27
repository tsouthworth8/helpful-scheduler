<template>
    <div id="app">
        <h1>This is the place where companies (managers) create templates, bro.</h1>

        <form id="shift-temp-form">
            <label for="start-hour">Start time:</label>
            <select id="start-hour" v-model="userInput.startHour">
                <option value="1">1:--</option>
                <option value="2">2:--</option>
                <option value="3">3:--</option>
                <option value="4">4:--</option>
                <option value="5">5:--</option>
                <option value="6">6:--</option>
                <option value="7">7:--</option>
                <option value="8">8:--</option>
                <option value="9">9:--</option>
                <option value="10">10:--</option>
                <option value="11">11:--</option>
                <option value="12">12:--</option>
            </select>
            <select id="start-minute" v-model="userInput.startMinute">
                <option value="00">-:00</option>
                <option value="15">-:15</option>
                <option value="30">-:30</option>
                <option value="45">-:45</option>
            </select>
            <select id="start-am" v-model="userInput.startAM">
                <option value=true>AM</option>
                <option value=flase>PM</option>
            </select>
            <br>

            <label for="start-hour">End time:</label>
            <select id="start-hour" v-model="userInput.endHour">
                <option value="1">1:--</option>
                <option value="2">2:--</option>
                <option value="3">3:--</option>
                <option value="4">4:--</option>
                <option value="5">5:--</option>
                <option value="6">6:--</option>
                <option value="7">7:--</option>
                <option value="8">8:--</option>
                <option value="9">9:--</option>
                <option value="10">10:--</option>
                <option value="11">11:--</option>
                <option value="12">12:--</option>
            </select>
            <select id="start-minute" v-model="userInput.endMinute">
                <option value="00">-:00</option>
                <option value="15">-:15</option>
                <option value="30">-:30</option>
                <option value="45">-:45</option>
            </select>
            <select id="start-am" v-model="userInput.endAM">
                <option value=true>AM</option>
                <option value=flase>PM</option>
            </select>
            <br>

            <div id="roles-list" v-for="role in shiftRoles" :key="role.id">
                <input type="checkbox" id="key" :value="role.id" v-model="shiftTemplate.allowedShiftRoles" /> {{role.title}}
            </div>

            <button id="submit-role-btn" @click.prevent="submitTemplate">Submit</button>
        </form>

        <h3>Template list:</h3>
        <p>{{templateList}}</p>
    </div>
</template>


<script>
import axios from "axios";

export default {
    name: 'company-templates',
    data() {
        return {
            shiftRoles: [
                {
                    id: 1,
                    companyId: 1,
                    title: 'Shift Supervisor'
                },
                {
                    id: 2,
                    companyId: 1,
                    title: 'Bartender'
                },
                {
                    id: 3,
                    companyId: 1,
                    title: 'Hostess'
                }
            ],
            userInput: {
                startHour: 0,
                startMinute: 0,
                startAM: true,
                endHour: 0,
                endMinute: 0,
                endAM: true
            },
            shiftTemplate: {
                startTime: '',
                endTime: '',
                allowedShiftRoles: []
            },
            templateList: []
        }
    },
    methods: {
        getShiftRoles() {
        },
        getTemplates() {
            axios.get(`${process.env.VUE_APP_REMOTE_API}/templates/getAll`, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("Authorization")
                }
            })
            .then(response => {
                console.log("Response reached");
                this.templateList = response.data;
            })
            .catch(err => {
                console.log(err);
            })
        },
        convertInput() {
            // if(this.userInput.startAM) {
            //     let startTime = new Date(1970, 0, 1, this.userInput.startHour, this.userInput.startMinute, 0, 0);
            //     this.shiftTemplate.startTime = startTime;
            // } else {
            //     let startTime = new Date(1970, 0, 1, this.userInput.startHour + 12, this.userInput.startMinute, 0, 0);
            //     this.shiftTemplate.startTime = startTime;
            // }

            // if(this.userInput.endAM) {
            //     let endTime = new Date(1970, 0, 1, this.userInput.endHour, this.userInput.endMinute, 0, 0);
            //     this.shiftTemplate.endTime = endTime;
            // } else {
            //     let endTime = new Date(1970, 0, 1, this.userInput.endHour + 12, this.userInput.endMinute, 0, 0);
            //     this.shiftTemplate.endTime = endTime;
            // }

            this.shiftTemplate.startTime = this.userInput.startHour + ":" + this.userInput.startMinute;
            this.shiftTemplate.endTime = this.userInput.endHour + ":" + this.userInput.endMinute;
        },
        submitTemplate() {
            this.convertInput();
            console.log(this.shiftTemplate);

            axios.post(`${process.env.VUE_APP_REMOTE_API}/templates/new`, this.shiftTemplate, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("Authorization")
                }
            })
            .then(response => {
                console.log(response);
            })
            .catch(err => {
                console.log(err);
            })
        }
    },
    mounted() {
        this.getTemplates();
    }
}
</script>


<style>
</style>