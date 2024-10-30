import os
from PIL import Image

def divide_image(image_path, frame_width, frame_height):
    try:
        sprite_sheet = Image.open(image_path)
        output_dir = f"{os.path.splitext(image_path)[0]}_frames"
        os.makedirs(output_dir, exist_ok=True)
        width, height = sprite_sheet.size
        rows = height // frame_height
        columns = width // frame_width

        for row in range(rows):
            row_dir = os.path.join(output_dir, str(row))
            os.makedirs(row_dir, exist_ok=True)
            for col in range(columns):
                left = col * frame_width
                upper = row * frame_height
                right = left + frame_width
                lower = upper + frame_height
                frame = sprite_sheet.crop((left, upper, right, lower))
                frame.save(os.path.join(row_dir, f"{col}.png"))
        print(f"Immagine divisa e salvata nella cartella '{output_dir}'")
    except Exception as e:
        print(f"Errore nella divisione dell'immagine: {e}")

def mirror_image(image_path):
    try:
        img = Image.open(image_path)
        mirrored_img = img.transpose(Image.FLIP_LEFT_RIGHT)
        mirrored_img.save(image_path)
        print(f"Immagine specchiata e sovrascritta: {image_path}")
    except Exception as e:
        print(f"Errore nella trasformazione dell'immagine '{image_path}': {e}")

def crop_center(image_path, target_width, target_height):
    try:
        img = Image.open(image_path)
        width, height = img.size
        left = (width - target_width) // 2
        top = (height - target_height) // 2
        right = left + target_width
        bottom = top + target_height
        cropped_img = img.crop((left, top, right, bottom))
        cropped_img.save(image_path)
        print(f"Immagine ritagliata al centro e sovrascritta: {image_path}")
    except Exception as e:
        print(f"Errore durante il ritaglio centrale dell'immagine '{image_path}': {e}")

def process_folder(folder_path, operation, frame_width=None, frame_height=None, target_width=None, target_height=None):
    for root, _, files in os.walk(folder_path):
        for filename in files:
            if filename.lower().endswith(('.png', '.jpg', '.jpeg', '.bmp', '.gif')):
                file_path = os.path.join(root, filename)
                if operation == 'divide' and frame_width and frame_height:
                    divide_image(file_path, frame_width, frame_height)
                elif operation == 'mirror':
                    mirror_image(file_path)
                elif operation == 'crop_center' and target_width and target_height:
                    crop_center(file_path, target_width, target_height)

def main():
    print("Seleziona l'operazione da eseguire:")
    print("1. Dividere un'immagine in frame")
    print("2. Specchiare un'immagine")
    print("3. Ritagliare l'immagine al centro")
    choice = input("Inserisci il numero dell'operazione desiderata (1, 2 o 3): ")

    if choice not in ['1', '2', '3']:
        print("Scelta non valida.")
        return

    path = input("Inserisci il percorso dell'immagine o della cartella: ")

    if choice == '1':
        frame_width = int(input("Inserisci la larghezza dei frame: "))
        frame_height = int(input("Inserisci l'altezza dei frame: "))
        if os.path.isfile(path):
            divide_image(path, frame_width, frame_height)
        elif os.path.isdir(path):
            process_folder(path, 'divide', frame_width=frame_width, frame_height=frame_height)
        else:
            print("Percorso non valido.")
    elif choice == '2':
        if os.path.isfile(path):
            mirror_image(path)
        elif os.path.isdir(path):
            process_folder(path, 'mirror')
        else:
            print("Percorso non valido.")
    elif choice == '3':
        target_width = int(input("Inserisci la larghezza del ritaglio centrale: "))
        target_height = int(input("Inserisci l'altezza del ritaglio centrale: "))
        if os.path.isfile(path):
            crop_center(path, target_width, target_height)
        elif os.path.isdir(path):
            process_folder(path, 'crop_center', target_width=target_width, target_height=target_height)
        else:
            print("Percorso non valido.")

if __name__ == "__main__":
    main()
