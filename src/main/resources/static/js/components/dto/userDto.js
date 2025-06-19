// Структура DTO и утилиты валидации

export function validateEmail(email) {
    const regex = /^[\w._%+-]+@mail\.com$/;
    return regex.test(email);
}

export function buildUserDtoFromForm(formEl) {
    const selectEl = formEl.querySelector('[name="roles"]');

    const roleNames = selectEl?.multiple
        ? Array.from(selectEl.selectedOptions).map(opt => opt.value)
        : Array.from(formEl.querySelectorAll('[name="roles"]:checked')).map(el => el.value);

    return {
        ID: formEl.querySelector('[name="ID"]')?.value || null,
        userName: formEl.querySelector('[name="userName"]').value.trim(),
        userEmail: formEl.querySelector('[name="userEmail"]').value.trim(),
        userPassword: formEl.querySelector('[name="userPassword"]')?.value || null,
        roleNames: roleNames
    };
}
