// Запросы к API

import { API } from "../config/apiEndpoints.js";

// Получение данных текущего пользователя
export async function getCurrentUser() {
    const resp = await fetch(API.CURRENT_USER, {
        method: 'GET',
        headers: { 'Accept': 'application/json' },
        credentials: 'include'
    });
    return resp.json();
}
// Получение списка пользователей для 'ADMIN'
export async function getAllUsers() {
    const res = await fetch(API.USERS, {
        method: 'GET',
        headers: { 'Accept': 'application/json' },
        credentials: 'include'
    });
    return res.json();
}
// Удаление пользователя
export async function deleteUser(userDto) {
    return await fetch(API.USERS, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        credentials: 'include',
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
        credentials: 'include',
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
        credentials: 'include',
        body: JSON.stringify(userDto)
    });
}
