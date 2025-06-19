// Управление панелью "Добавление пользователя"

import { addNewUser } from '../../services/adminService.js';
import { validateEmail } from '../../components/dto/userDto.js';

const availableRoles = ['USER', 'ADMIN'];

export function initAddUserForm() {
    const roleSelect = document.getElementById('addRole');
    if (!roleSelect) return;
    roleSelect.innerHTML = '';

    if (roleSelect.options.length === 0) {
        availableRoles.forEach(role => {
            const option = document.createElement('option');
            option.value = role;
            option.textContent = role;
            roleSelect.appendChild(option);
        });
    }

    const addBtn = document.getElementById('addUserBtn');
    if (addBtn.dataset.bound) return;
    addBtn.dataset.bound = 'true';

    addBtn.addEventListener('click', async () => {
        const form = document.getElementById('userForm');

        const email = form.querySelector('[name="userEmail"]').value.trim();
        if (!validateEmail(email)) {
            alert('Неверный формат email. Допустим только *@mail.com');
            return;
        }

        await addNewUser();

        const listTabBtn = document.querySelector('#admin-tab');
        const tab = new bootstrap.Tab(listTabBtn);
        tab.show();
    });
}
