// Запросы к API

import { API } from "../config/apiEndpoints.js";

// Получение данных текущего пользователя
export async function getCurrentUser() {
    const resp = await fetch(API.CURRENT_USER, { // 1 промис - Promise<Response>
        method: 'GET',
        headers: { 'Accept': 'application/json' },
        // credentials: 'include' // нужен при кросс-доменных запросах с сохранением и передачей кукис
    });
    if (!resp.ok) {
        const errorText = await resp.text(); // 2 промис с текстом ошибки (или в json)
        throw new Error(`Ошибка HTTP ${resp.status}: ${errorText}`);
    }
    const data = await resp.json(); // 3 промис - Promise<userDto> (JavaScript-объект)
    console.log(data);
    return data;
}
// Получение списка пользователей для 'ADMIN'
export async function getAllUsers() {
    const resp = await fetch(API.USERS, {
        method: 'GET',
        headers: { 'Accept': 'application/json' },
    });
    return resp.json();
}
// Удаление пользователя
export async function deleteUser(userDto) {
    return await fetch(API.USERS, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(userDto)
    });
}
// Редактирование пользователя
export async function editUser(userDto) {
    return await fetch(API.USERS, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(userDto)
    });
}
// Добавление нового пользователя
export async function addUser(userDto) {
    return await fetch(API.USERS, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify(userDto)
    });
}
