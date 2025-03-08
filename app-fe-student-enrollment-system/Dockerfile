# Etapa 1: Construcción de la aplicación Angular
# Utiliza la imagen de Node.js 20.x para compilar la aplicación
FROM node:20-alpine AS build

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar package.json y package-lock.json para instalar las dependencias
COPY package*.json ./

# Instalar las dependencias
RUN npm install

# Copiar todo el código de la aplicación
COPY . .

# Compilar la aplicación Angular para producción
RUN npm run build -- --configuration production

# Etapa 2: Servir la aplicación Angular con Nginx
FROM nginx:alpine

# Copiar los archivos generados desde la etapa de compilación a Nginx
COPY --from=build /app/dist/app-fe-student-enrollment-system /usr/share/nginx/html

# Exponer el puerto 80
EXPOSE 80

# Iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]
