<template>
    <div id="app">
        <h1>This is the place where companies (managers) create templates, bro.</h1>

        <div class="box">
        <h3>Create a shift template</h3>
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
                <option :value="true">AM</option>
                <option :value="false">PM</option>
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
                <option :value="true">AM</option>
                <option :value="false">PM</option>
            </select>
            <br>

            <label for="roles-list">Allowed Shift Roles:</label>
            <div id="roles-list" v-for="role in shiftRoles.data" :key="role.id">
                <input type="checkbox" id="key" :value="role.id" v-model="shiftTemplate.allowedShiftRoles" /> {{role.title}}
            </div>

            <button id="submit-role-btn" @click.prevent="submitShiftTemplate">Submit</button>
        </form>
        </div>

        <div class="box">
        <h3>Shift templates</h3>
        <table id="shift-template-table">
            <th></th>
            <th>Start time</th>
            <th>End time</th>
            <th>Allowed Shift Roles</th>
            <tr v-for="template in shiftTemplateList" :key="template.id">
                <td>
                    <label>
    					<input type="checkbox" :value="template.id" v-model="selectedShiftTemplates">
  					</label>
                </td>
                <td>{{template.startTime.hour + ":" + template.startTime.minute}}</td>
                <td>{{template.endTime.hour + ":" + template.endTime.minute}}</td>
                <td><p v-for="role in template.allowedShiftRoles" :key="role.id">{{role.title}}</p></td>
            </tr>
        </table>

        <button @click.prevent="deleteShiftTemplates()">Delete Selected</button>
        <button @click.prevent="createDayTemplate()">Day template from selected</button>

            <form id="day-template-form" v-if="showDayTempForm">
                <label for="day-temp-nickname">Day Template Name:</label>
                <input type="text" v-model="dayTemplate.nickname"></input>

                <button id="day-template-submit" @click.prevent="submitDayTemplate">Submit</button>
            </form>
        </div>

        <div class="box">
        <h3>Day templates</h3>
        

        <button @click.prevent="deleteTemplates()">Delete Selected</button>
        </div>

    <h1>this.dayTemplate: {{this.dayTemplate}}</h1>
    </div>
</template>


<script>
import axios from "axios";

export default {
    name: 'company-templates',
    data() {
        return {
            shiftRoles: [],
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
            shiftTemplateList: [],
            selectedShiftTemplates: [],
            showDayTempForm: false,
            dayTemplate: {
                id: 0,
                nickname: '',
                shiftIds: []
            }
        }
    },
    methods: {
        getShiftRoles() {
            axios.get(`${process.env.VUE_APP_REMOTE_API}/api/getRoles`, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("Authorization")
                }
            })
            .then(response => {
                console.log("Role response");
                this.shiftRoles = response;
            })
            .catch(err => {
                console.log(err);
            })
        },
        getShiftTemplates() {
            axios.get(`${process.env.VUE_APP_REMOTE_API}/templates/getAll`, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("Authorization")
                }
            })
            .then(response => {
                console.log("Templates response reached");
                console.log(response)
                response.data.forEach(element => {
                    if(element.endTime.minute == 0) {
                        element.endTime.minute = '00';
                    }
                    if(element.startTime.minute == 0) {
                        element.startTime.minute = '00';
                    }
                })
                this.shiftTemplateList = response.data;
            })
            .catch(err => {
                console.log(err);
            })
        },
        convertInput() {
            if(this.userInput.startAM) {
                this.shiftTemplate.startTime = this.userInput.startHour + ":" + this.userInput.startMinute;
            } else {
                let hour = parseInt(this.userInput.startHour) + 12;
                this.shiftTemplate.startTime = hour + ":" + this.userInput.startMinute;
            }

            if(this.userInput.endAM) {
                this.shiftTemplate.endTime = this.userInput.endHour + ":" + this.userInput.endMinute;
            } else {
                let hour = parseInt(this.userInput.endHour) + 12;
                this.shiftTemplate.endTime = hour + ":" + this.userInput.endMinute;
            }
        },
        submitShiftTemplate() {
            this.convertInput();
            console.log(this.shiftTemplate);

            axios.post(`${process.env.VUE_APP_REMOTE_API}/templates/new`, this.shiftTemplate, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("Authorization")
                }
            })
            .then(response => {
                console.log(response);
                this.getShiftTemplates();
                this.clearShiftTemplateForm();
            })
            .catch(err => {
                console.log(err);
            })
        },
        clearShiftTemplateForm() {
            this.shiftTemplate.startTime = '';
            this.shiftTemplate.endTime = '';
            this.shiftTemplate.allowedShiftRoles = [];
        },
        deleteShiftTemplates() {
            console.log("Delete templates method reached.")
            axios.post(`${process.env.VUE_APP_REMOTE_API}/templates/delete`, this.selectedShiftTemplates, {
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("Authorization")
                }
            })
            .then(response => {
                console.log(response);
                this.getShiftTemplates();
                this.selectedShiftTemplates = [];
            })
            .catch(err => {
                console.log(err);
            })
        },
        createDayTemplate() {
            this.showDayTempForm = true;
        },
        submitDayTemplate() {
            this.dayTemplate.shiftIds = this.selectedShiftTemplates;

            console.log(this.dayTemplate);

            axios.post(`${process.env.VUE_APP_REMOTE_API}/templates/newDay`, this.dayTemplate, {
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
        this.getShiftTemplates();
        this.getShiftRoles();
    }
}
</script>


<style>
h3 {
    font-weight: bold;
    text-align: center;
}
</style>