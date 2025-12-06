# Stage 1: Construim aplicația React cu Node
FROM node:20-alpine as build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# Această comandă creează folderul 'dist' cu fișierele statice optimizate
RUN npm run build

# Stage 2: Servim aplicația cu Nginx
FROM nginx:alpine
# Copiem fișierele generate (dist) din etapa anterioară în folderul Nginx
COPY --from=build /app/dist /usr/share/nginx/html

# (Opțional) Copiem o configurare custom pentru a trata routing-ul React
# Dacă nu ai acest fișier, poți sări peste linia asta, dar la refresh pe pagini interne vei primi 404
# COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]