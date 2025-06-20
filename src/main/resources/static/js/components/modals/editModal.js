// Модальное окно редактирования пользователя

import {editCurrentUser} from "../../services/adminService.js";
import {validateEmail} from "../dto/userDto.js";

const availableRoles = ['USER', 'ADMIN'];

export function bindEditModal(button) {
    button.addEventListener('click', () => {
        const row = button.closest('tr');

        const userData = {
            ID: row.children[0].textContent.trim(),
            userName: row.children[1].textContent.trim(),
            userEmail: row.children[2].textContent.trim(),
            roleNames: row.children[3].textContent.trim().split(',').map(r => r.trim())
        };

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
    form.addEventListener('submit', async (e) => {
        e.preventDefault();

        const email = form.querySelector('[name="userEmail"]').value.trim();
        if (!validateEmail(email)) {
            alert('Неверный формат email. Допустим только *@mail.com');
            return;
        }

        await editCurrentUser();
    });

}
