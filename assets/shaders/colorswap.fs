#version 330

// Inputs von raylib (Vertex Shader)
in vec2 fragTexCoord;
in vec4 fragColor;

// Outputs
out vec4 finalColor;

// Uniforms
uniform sampler2D texture0; // Entspricht 'bitmap' in Flixel/raylib
uniform vec3 uTime;          // Zeit-Vektor (uTime.x wird meist als Gesamtzeit genutzt)
uniform bool awesomeOutline; // Flag für die Outline

// Konstanten
const float offset = 1.0 / 128.0;

// Farbkonvertierung: RGB zu HSV
vec3 rgb2hsv(vec3 c)
{
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

// Farbkonvertierung: HSV zu RGB
vec3 hsv2rgb(vec3 c)
{
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main()
{
    // Basis-Texturfarbe holen
    vec4 texColor = texture(texture0, fragTexCoord);
    
    // Berechne den Regenbogeneffekt basierend auf uTime.x (Zeit in Sekunden)
    // Wenn uTime ein vec3 ist, nutzen wir uTime.x als fortschreitenden Wert.
    vec3 hsv = rgb2hsv(texColor.rgb);
    hsv.x += uTime.x; // Verschiebe den Farbton (Hue) über die Zeit
    vec3 rainbowColor = hsv2rgb(hsv);

    if (awesomeOutline) 
    {
        // Einfache 4-Punkt-Outline-Überprüfung (Nachbarpixel)
        float alphaUp    = texture(texture0, fragTexCoord + vec2(0.0, offset)).a;
        float alphaDown  = texture(texture0, fragTexCoord - vec2(0.0, offset)).a;
        float alphaLeft  = texture(texture0, fragTexCoord - vec2(offset, 0.0)).a;
        float alphaRight = texture(texture0, fragTexCoord + vec2(offset, 0.0)).a;
        
        // Wenn der aktuelle Pixel transparent ist, aber ein Nachbarpixel sichtbar ist -> Outline zeichnen
        if (texColor.a == 0.0 && (alphaUp > 0.0 || alphaDown > 0.0 || alphaLeft > 0.0 || alphaRight > 0.0)) 
        {
            // Outline bekommt die Regenbogenfarbe (voll sichtbar)
            finalColor = vec4(rainbowColor, 1.0) * fragColor;
            return;
        }
    }

    // Wenn keine Outline gezeichnet wird, normale Texturfarbe mit Regenbogeneffekt multiplizieren
    // Falls der Effekt nur die Outline betreffen soll, hier einfach: finalColor = texColor * fragColor;
    finalColor = vec4(rainbowColor, texColor.a) * fragColor;
}
