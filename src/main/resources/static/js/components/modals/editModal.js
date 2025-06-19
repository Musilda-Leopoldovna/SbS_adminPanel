// Модальное окно редактирования пользователя

import {editCurrentUser} from "../../services/adminService.js";

const availableRoles = ['USER', 'ADMIN'];

export function bindEditModal(button, userData) {
    button.addEventListener('click', () => {
        const form = document.getElementById('editUserForm');

        form.querySelector('[name="ID"]').value = userData.ID;
        form.querySelector('[name="userName"]').value = userData.userName;
        form.querySelector('[name="userEmail"]').value = userData.userEmail;
        form.querySelector('[name="userPassword"]').value = '';

        const roleSelect = form.querySelector('[name="roles"]');
        roleSelect.innerHTML = '';

        availableRoles.forEach(role => {
            const option = document.createElement('option');
            option.value = role;
            option.textContent = role;
            option.selected = userData.roleNames.includes(role);
            roleSelect.appendChild(option);
        });
    });
}

export function bindEditConfirmButton() {
    const form = document.getElementById('editUserForm');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        await editCurrentUser();
    });
}
