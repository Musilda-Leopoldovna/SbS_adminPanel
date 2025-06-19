// Логика шаблона `/admin`

import { getAllUsers } from '../../services/apiClient.js';
import { bindEditConfirmButton, bindEditModal } from '../../components/modals/editModal.js';
import { bindDeleteConfirmButton, bindDeleteModal } from '../../components/modals/deleteModal.js';
import {initAddUserForm} from "./userAddTab.js";

export async function initAdminPage(user) {
    if (!user.roleNames.includes('ADMIN')) return;

    const users = await getAllUsers();
    const allUsersTbody = document.getElementById('users-data');
    allUsersTbody.innerHTML = '';

    users.forEach(u => {
        const userRow = document.createElement('tr');
        userRow.setAttribute('data-id', u.ID);

        const roles = (u.roleNames && u.roleNames.length > 0)
            ? u.roleNames.join(', ')
            : 'Права не назначены';

        userRow.innerHTML = `
            <td>${u.ID}</td>
            <td>${u.userName}</td>
            <td>${u.userEmail}</td>
            <td>${roles}</td>
            <td>
                <div class="button-group">
                    <button type="button" class="update_button"
                            data-bs-toggle="modal"
                            data-bs-target="#editForm"
                            title="редактировать">upd</button>
                    <button type="button" class="delete_button"
                            data-bs-toggle="modal"
                            data-bs-target="#deleteForm"
                            title="удалить">del</button>
                </div>
            </td>`;
        allUsersTbody.appendChild(userRow);

        const editBtn = userRow.querySelector('.update_button');
        const deleteBtn = userRow.querySelector('.delete_button');

        bindEditModal(editBtn, u);
        bindDeleteModal(deleteBtn, u);
    });
    bindDeleteConfirmButton();
    bindEditConfirmButton();
    initAddUserForm();
}
