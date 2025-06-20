
import { deleteUser, editUser, addUser } from './apiClient.js';
import { buildUserDtoFromForm } from '../components/dto/userDto.js';
import { closeModal, removeRowById, updateRowById } from '../components/utils/domUtils.js';
import { appendNewUserRow } from '../components/utils/formUtils.js'

export async function deleteCurrentUser() {
    const form = document.getElementById('deleteUserForm');
    const userDto = buildUserDtoFromForm(form);

    try {
        const response = await deleteUser(userDto);

        if (response.status === 204) {
            removeRowById(userDto.ID);
            closeModal('deleteForm');
        } else {
            const errorText = await response.text();
            alert(`Ошибка удаления: ${errorText}`);
        }
    } catch (error) {
        console.error('Ошибка при удалении:', error);
        alert('Ошибка удаления пользователя');
    }
}

export async function editCurrentUser() {
    const form = document.getElementById('editUserForm');
    const userDto = buildUserDtoFromForm(form);

    try {
        const response = await editUser(userDto);

        if (response.ok) {
            const updatedUser = await response.json();
            updateRowById(updatedUser);
            closeModal('editForm');
        }
    } catch (error) {
        console.error('Ошибка при редактировании:', error);
        alert('Не удалось изменить пользователя');
    }
}

export async function addNewUser() {
    const form = document.getElementById('userForm');
    const userDto = buildUserDtoFromForm(form);

    try {
        const addResponse = await addUser(userDto);

        if (addResponse.ok) {
            const newUser = await addResponse.json();
            console.log("Пользователь добавлен:", newUser);
            appendNewUserRow(newUser);
            form.reset();
            closeModal('userForm');
        } else {
            const errorText = await addResponse.text();
            alert(`Ошибка добавления пользователя: ${errorText}`);
        }
    } catch (error) {
        console.error('Ошибка при добавлении:', error);
        alert('Не удалось добавить пользователя');
    }
}
