# Neon Void — Publicar GRÁTIS (Android + iPhone) com atualizações

Como funciona:
- Pasta `docs/` = o jogo (versão web/PWA). Abre no iPhone e no Android.
- O APK Android é gerado AUTOMATICAMENTE pelo GitHub e usa o MESMO jogo da pasta `docs/`.
- Para atualizar: você troca `docs/index.html` no GitHub e pronto. Ninguém precisa desinstalar.

## PARTE 1 — Publicar (uma vez só)
1. Crie conta grátis em github.com.
2. Clique em **New repository** → nome `neonvoid` → marque **Public** (o Pages grátis exige público) → Create.
3. Clique em **uploading an existing file**. Descompacte o zip no computador e arraste TODO o conteúdo da pasta
   `neonvoid_android_only` (incluindo `docs`, `app` e a pasta oculta `.github`) → **Commit changes**.
   Dica: se a pasta `.github` não subir pelo navegador, use o app GitHub Desktop.
4. **Settings > Pages**: Source = *Deploy from a branch* → Branch = `main`, pasta `/docs` → Save.
   Em ~1 minuto seu jogo fica em: `https://SEU-USUARIO.github.io/neonvoid/`
5. **Settings > Secrets and variables > Actions > New repository secret**. Crie 4 segredos com os dados do arquivo
   `SEGREDOS.txt` (do outro zip, a chave): `KEYSTORE_BASE64`, `KEYSTORE_PASSWORD`, `KEY_ALIAS`, `KEY_PASSWORD`.
   ⚠️ NUNCA suba o zip da chave para o repositório.
6. Aba **Actions** → "Gerar APK e publicar release" → **Run workflow**. (Se ela já rodou e falhou por falta dos segredos, rode de novo.)
7. Quando ficar verde, a aba **Releases** terá o `NeonVoid.apk` (e `NeonVoid.aab` para Play Store no futuro).

## PARTE 2 — O que mandar para as pessoas
Mande o link: `https://SEU-USUARIO.github.io/neonvoid/instalar.html`
- **iPhone:** Safari → Compartilhar → Adicionar à Tela de Início.
- **Android:** botão "Baixar APK" (instala app de verdade) ou Chrome → ⋮ → Instalar app.

## PARTE 3 — Mandar atualização (sem desinstalar)
1. No GitHub, abra `docs/index.html` → ícone do lápis (ou Add file > Upload files e substitua) → Commit.
2. **Web/iPhone/Android pelo Chrome:** atualiza sozinho na próxima vez que a pessoa abrir o jogo com internet.
3. **APK:** o GitHub gera automaticamente uma nova release (v2, v3...). A pessoa baixa o novo APK e instala POR CIMA do antigo
   — o progresso é mantido. Isso só funciona porque todos os APKs são assinados com a MESMA chave e o número de versão sobe sozinho.
   Link direto sempre da última versão: `https://github.com/SEU-USUARIO/neonvoid/releases/latest/download/NeonVoid.apk`

## Cuidados
- Guarde a chave (`neonvoid-release.jks` + senhas) em 2 lugares. Se perder, só desinstalando dá para atualizar.
- Não instale APKs "debug" por cima de "release" (assinaturas diferentes) — use sempre o da Release.
- Repositório público = qualquer um vê o código e as imagens. A foto de rosto dentro do jogo exige autorização da pessoa.
- O ícone "NV" é provisório: troque os PNGs em `docs/icons/` e `app/src/main/res/mipmap-*/`.
- Se quiser publicar na Google Play depois (US$ 25), use o `NeonVoid.aab` da Release.
