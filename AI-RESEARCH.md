| Model     | Kanał      | Zakres matematyczny        |
|-----------|------------|----------------------------|
| **RGB**   | R, G, B    | 0–1 lub 0–255              |
| **HSV**   | H          | 0–360°                     |
|           | S          | 0–1 lub 0–100%             |
|           | V          | 0–1 lub 0–100%             |
| **HSL**   | H          | 0–360°                     |
|           | S          | 0–1 lub 0–100%             |
|           | L          | 0–1 lub 0–100%             |
| **HSI**   | H          | 0–360°                     |
|           | S          | 0–1 lub 0–100%             |
|           | I          | 0–1 lub 0–100%             |
| **CMY**   | C, M, Y    | 0–1 lub 0–100%             |
| **CMYK**  | C, M, Y, K | 0–1 lub 0–100%             |
| **YCbCr** | Y          | 0–1 lub 0–255              |
|           | Cb, Cr     | około −0,5–0,5 lub 0–255\* |
| **YUV**   | Y          | 0–1                        |
|           | U, V       | około −0,5–0,5             |
| **YIQ**   | Y          | 0–1                        |
|           | I          | około −0,6–0,6             |
|           | Q          | około −0,5–0,5             |

| Sposób zapisu | Kanały     | Typowa liczba bitów na kanał | Zakres wartości kanału       | Łącznie       |
|---------------|------------|------------------------------|------------------------------|---------------|
| **RGB**       | R, G, B    | **8 bitów**                  | 0–255                        | **24 bity**   |
| **HSV**       | H, S, V    | **8 bitów**\*                | H: 0–255, S: 0–255, V: 0–255 | **24 bity**\* |
| **HSL**       | H, S, L    | **8 bitów**                  | 0–255                        | **24 bity**   |
| **HSI**       | H, S, I    | **8 bitów**                  | 0–255                        | **24 bity**   |
| **CMY**       | C, M, Y    | **8 bitów**                  | 0–255                        | **24 bity**   |
| **CMYK**      | C, M, Y, K | **8 bitów**                  | 0–255                        | **32 bity**   |
| **YCbCr**     | Y, Cb, Cr  | **8 bitów**                  | 0–255                        | **24 bity**   |
| **YUV**       | Y, U, V    | **8 bitów**                  | zależnie od reprezentacji    | **24 bity**   |
| **YIQ**       | Y, I, Q    | **8 bitów**                  | zależnie od reprezentacji    | **24 bity**   |

\* W HSV kanał **H (Hue)** jest często przechowywany w 8 bitach, ale jego rzeczywisty zakres semantyczny to **0–360°**. Przy 8-bitowym zapisie trzeba więc przeskalować 360° do 256 możliwych wartości.

| Model | Kanał | Znaczenie             | Typowy zakres przy 8 bitach |
|-------|-------|-----------------------|-----------------------------|
| RGB   | R     | czerwony              | 0–255                       |
| RGB   | G     | zielony               | 0–255                       |
| RGB   | B     | niebieski             | 0–255                       |
| HSV   | H     | odcień                | 0–360°\*                    |
| HSV   | S     | nasycenie             | 0–100%                      |
| HSV   | V     | wartość/jasność       | 0–100%                      |
| HSL   | H     | odcień                | 0–360°\*                    |
| HSL   | S     | nasycenie             | 0–100%                      |
| HSL   | L     | jasność               | 0–100%                      |
| HSI   | H     | odcień                | 0–360°\*                    |
| HSI   | S     | nasycenie             | 0–100%                      |
| HSI   | I     | intensywność          | 0–100%                      |
| CMY   | C     | cyjan                 | 0–255                       |
| CMY   | M     | magenta               | 0–255                       |
| CMY   | Y     | żółty                 | 0–255                       |
| CMYK  | C     | cyjan                 | 0–255                       |
| CMYK  | M     | magenta               | 0–255                       |
| CMYK  | Y     | żółty                 | 0–255                       |
| CMYK  | K     | czarny                | 0–255                       |
| YCbCr | Y     | luminancja            | 0–255\*\*                   |
| YCbCr | Cb    | chrominancja          | 0–255\*\*                   |
| YCbCr | Cr    | chrominancja          | 0–255\*\*                   |
| YUV   | Y     | luminancja            | zależnie od standardu       |
| YUV   | U     | chrominancja          | zależnie od standardu       |
| YUV   | V     | chrominancja          | zależnie od standardu       |
| YIQ   | Y     | luminancja            | zależnie od reprezentacji   |
| YIQ   | I     | składowa chromatyczna | zależnie od reprezentacji   |
| YIQ   | Q     | składowa chromatyczna | zależnie od reprezentacji   |

\* W praktycznym zapisie 8-bitowym H jest **skalowany**, bo 8 bitów daje tylko 256 poziomów, a odcień ma 360°.\
\*\* W YCbCr zakresy zależą od konkretnego standardu, np. **full range** i **limited/video range**.
