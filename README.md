# Android - sample handling unauthorized tokens

This repository demonstrates handling unauthorized tokens.

- Refresh the access token using the refresh token.
- Retry refresh token 3 times.
- If expired tokens, force logout and show login screen.

See [this implementation](https://github.com/STAR-ZERO/android-unauthorized-sample/blob/main/app/src/main/java/com/star_zero/unauthorizedsample/api/AccessTokenAuthenticator.kt) for details
