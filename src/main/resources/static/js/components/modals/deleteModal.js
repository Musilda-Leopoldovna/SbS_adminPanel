// Модальное окно удаления пользователя (логика заполнения)

import { deleteCurrentUser } from '../../services/adminService.js';

export function bindDeleteModal(button, userData) {
    button.addEventListener('click', () => {
        const form = document.getElementById('deleteUserForm');

        form.querySelector('[name="ID"]').value = userData.ID;
        form.querySelector('[name="userName"]').value = userData.userName;
        form.querySelector('[name="userEmail"]').value = userData.userEmail;
    });
}

export function bindDeleteConfirmButton() {
    document.getElementById('confirmDeleteBtn').addEventListener('click', deleteCurrentUser);
}
