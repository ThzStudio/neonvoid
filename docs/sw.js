// Service worker: rede primeiro (pega atualização sempre que tiver internet), cache como reserva (offline).
const CACHE = 'neonvoid-cache-v1';
const ARQUIVOS = ['./', 'index.html', 'manifest.json', 'icons/icon-192.png', 'icons/icon-512.png', 'icons/apple-touch-icon.png'];
self.addEventListener('install', e => {
  e.waitUntil(caches.open(CACHE).then(c => c.addAll(ARQUIVOS)).then(() => self.skipWaiting()));
});
self.addEventListener('activate', e => {
  e.waitUntil(caches.keys().then(ks => Promise.all(ks.filter(k => k !== CACHE).map(k => caches.delete(k)))).then(() => self.clients.claim()));
});
self.addEventListener('fetch', e => {
  const r = e.request;
  if (r.method !== 'GET' || new URL(r.url).origin !== location.origin) return;
  e.respondWith(
    fetch(r, { cache: 'no-cache' }).then(res => {
      const copia = res.clone();
      caches.open(CACHE).then(c => c.put(r, copia));
      return res;
    }).catch(() => caches.match(r).then(m => m || caches.match('index.html')))
  );
});
