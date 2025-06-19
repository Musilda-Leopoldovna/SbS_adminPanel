// Преобразование данных из форм

import {bindEditModal} from "../modals/editModal.js";
import {bindDeleteModal} from "../modals/deleteModal.js";

export function appendNewUserRow(user) {
    const userBody = document.getElementById('users-data')
    const tr = document.createElement('tr');
    tr.setAttribute('data-id', user.ID);

    const roles = (user.roleNames && user.roleNames.length > 0)
        ? user.roleNames.join(', ')
        : 'Права не назначены';

    tr.innerHTML = `
            <td>${user.ID}</td>
            <td>${user.userName}</td>
            <td>${user.userEmail}</td>
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
    userBody.appendChild(tr);

    const updateBtn = tr.querySelector('.update_button');
    const deleteBtn = tr.querySelector('.delete_button');
    bindEditModal(updateBtn, user);
    bindDeleteModal(deleteBtn, user);
}
