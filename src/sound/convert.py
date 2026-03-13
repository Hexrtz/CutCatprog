import urllib.request
import certifi
import ssl
import sys

# Force certifi certs for urllib
context = ssl.create_default_context(cafile=certifi.where())

try:
    urllib.request.urlretrieve('https://johnvansickle.com/ffmpeg/releases/ffmpeg-release-amd64-static.tar.xz', 'ffmpeg.tar.xz', context=context)
except Exception as e:
    print(e)
    sys.exit(1)
