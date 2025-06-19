// Точка входа, инициализация всего проекта
import { getCurrentUser } from './services/apiClient.js';
import { renderHeader } from './components/header.js';
import { renderSidebar } from './components/sidebar.js';
import { initUserPage } from './pages/userPage.js';
import { initAdminPage } from './pages/admin/adminPage.js';

document.addEventListener('DOMContentLoaded', async () => {
    try {
        const user = await getCurrentUser();
        const currentPath = window.location.pathname;
        renderHeader(user);
        renderSidebar(user, currentPath);

        if (currentPath === '/admin') {
            if (user.roleNames.includes('ADMIN')) {
                await initAdminPage(user);
            } else {
                window.location.href = 'Вы не авторизованы для доступа к этому ресурсу.';
            }
        } else if (currentPath === '/user') {
            initUserPage(user);
        }
    } catch (e) {
        console.error('Ошибка загрузки текущего пользователя:', e);
        alert('Не удалось загрузить данные пользователя. Повторите попытку позже.');
    }
});
