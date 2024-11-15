document.addEventListener("DOMContentLoaded", function() {
    const dropZone = document.getElementById('drop-zone');
    const fileInput = document.getElementById('file-input');
    const previewImage = document.getElementById('preview-image');

    // Обработка события перетаскивания
    dropZone.addEventListener('dragover', function(e) {
        e.preventDefault(); // Остановить стандартное поведение
        dropZone.classList.add('dragover');
    });

    dropZone.addEventListener('dragleave', function() {
        dropZone.classList.remove('dragover');
    });

    dropZone.addEventListener('drop', function(e) {
        e.preventDefault(); // Остановить стандартное поведение
        dropZone.classList.remove('dragover');

        const file = e.dataTransfer.files[0];
        if (file && file.type.startsWith('image/')) {
            fileInput.files = e.dataTransfer.files; // Заполняем input с файлом
            previewImage.style.display = 'block'; // Показываем превью изображения
            previewImage.src = URL.createObjectURL(file); // Показываем картинку в превью
        } else {
            alert("Пожалуйста, выберите изображение.");
        }
    });

    // Обработка клика для выбора файла
    fileInput.addEventListener('change', function() {
        const file = fileInput.files[0];
        if (file && file.type.startsWith('image/')) {
            previewImage.style.display = 'block'; // Показываем превью изображения
            previewImage.src = URL.createObjectURL(file); // Показываем картинку в превью
        } else {
            alert("Пожалуйста, выберите изображение.");
        }
    });
});
