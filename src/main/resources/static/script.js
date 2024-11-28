const userTableBody = document.getElementById("userTableBody");
const addUserForm = document.getElementById("addUserForm");
const editUserForm = document.getElementById("editUserForm");
const deleteUserForm = document.getElementById("deleteUserForm");

// Функция загрузки пользователей и заполнения таблицы
function loadUsers() {
    fetch('/api/users')
        .then(response => response.json())
        .then(users => {
            userTableBody.innerHTML = ''; // Очистка таблицы
            users.forEach(user => {
                const row = `
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.join(", ")}</td>
                        <td>
                            <button class="btn btn-sm btn-primary" onclick="openEditModal(${user.id})">Edit</button>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-danger" onclick="openDeleteModal(${user.id})">Delete</button>
                        </td>
                    </tr>
                `;
                userTableBody.innerHTML += row;
            });
        })
        .catch(error => console.error('Ошибка загрузки пользователей:', error));
}

// Добавление нового пользователя
addUserForm.addEventListener('submit', function (event) {
    event.preventDefault();
    const formData = new FormData(addUserForm);
    const user = {
        firstName: formData.get('firstName'),
        lastName: formData.get('lastName'),
        email: formData.get('email'),
        password: formData.get('password'),
        roles: [formData.get('role')]  // Преобразуем роль в массив
    };

    fetch('/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (response.ok) {
                loadUsers();
                addUserForm.reset(); // Очистить форму
            } else {
                alert('Ошибка при добавлении пользователя.');
            }
        })
        .catch(error => console.error('Ошибка:', error));
});

// Открытие модального окна редактирования
function openEditModal(userId) {
    fetch(`/api/users/${userId}`)
        .then(response => response.json())
        .then(user => {
            document.getElementById('editUserId').value = user.id;
            document.getElementById('editFirstName').value = user.firstName;
            document.getElementById('editLastName').value = user.lastName;
            document.getElementById('editEmail').value = user.email;
            document.getElementById('editRole').value = user.roles[0];  // Если roles массив, берем первый элемент
            const editModal = new bootstrap.Modal(document.getElementById('editUserModal'));
            editModal.show();
        })
        .catch(error => console.error('Ошибка при загрузке пользователя:', error));
}

// Сохранение изменений пользователя
document.getElementById('saveUserChanges').addEventListener('click', function () {
    const userId = document.getElementById('editUserId').value;
    const user = {
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        email: document.getElementById('editEmail').value,
        roles: [document.getElementById('editRole').value]  // Роли передаются как массив
    };

    fetch(`/api/users/${userId}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (response.ok) {
                loadUsers();
                const editModal = bootstrap.Modal.getInstance(document.getElementById('editUserModal'));
                editModal.hide();
            } else {
                alert('Ошибка при обновлении пользователя.');
            }
        })
        .catch(error => console.error('Ошибка:', error));
});

// Открытие модального окна удаления
function openDeleteModal(userId) {
    document.getElementById('deleteUserId').value = userId;
    const deleteModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));
    deleteModal.show();
}

// Удаление пользователя
document.getElementById('confirmDeleteUser').addEventListener('click', function () {
    const userId = document.getElementById('deleteUserId').value;

    fetch(`/api/users/${userId}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                loadUsers();
                const deleteModal = bootstrap.Modal.getInstance(document.getElementById('deleteUserModal'));
                deleteModal.hide();
            } else {
                alert('Ошибка при удалении пользователя.');
            }
        })
        .catch(error => console.error('Ошибка:', error));
});

// Загрузка пользователей при старте страницы
loadUsers();

