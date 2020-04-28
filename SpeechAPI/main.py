from google.cloud import vision
from google.cloud import storage
from google.cloud import speech_v1p1beta1 as speech
from google.cloud import texttospeech_v1 as texttospeech

import os


def text_to_speech():
        with open("introduction.txt", "r") as intro_ssml:
            ssml_text = intro_ssml.read()

        input = texttospeech.types.SynthesisInput(ssml=ssml_text)

        # voice = texttospeech.types.VoiceSelectionParams(
        #     language_code='uk-UA',
        #     name='uk-UA-Wavenet-A',
        #     ssml_gender=texttospeech.enums.SsmlVoiceGender.FEMALE)
        voice = texttospeech.types.VoiceSelectionParams(
            language_code='en-US',
            # name='uk-UA-Wavenet-A',
            ssml_gender=texttospeech.enums.SsmlVoiceGender.MALE)

        audio_config = texttospeech.types.AudioConfig(audio_encoding=texttospeech.enums.AudioEncoding.MP3)

        client = texttospeech.TextToSpeechClient()
        # print(client.list_voices())

        response = client.synthesize_speech(input, voice, audio_config)

        with open('output.mp3', 'wb') as out:
            # Write the response to the output file.
            out.write(response.audio_content)
            print('Audio content written to file "output.mp3"')


def speech_to_text():
    client = speech.SpeechClient()

    language = 'en-US'
    sample_rate = 24000

    config = {
        "language_code": language,
        "sample_rate_hertz": sample_rate,
        "encoding": speech.enums.RecognitionConfig.AudioEncoding.MP3,
    }

    audio = {
        "uri": "gs://tym-chud-general-bucket/output.mp3"
    }

    response = client.recognize(config, audio)
    for result in response.results:
        print(result)


def upload_to_storage():
    storage_client = storage.Client()
    bucket_name = "tym-chud-general-bucket"
    file_name = "output.mp3"

    bucket = storage_client.bucket(bucket_name)

    file_blob = bucket.blob(file_name)
    file_blob.upload_from_filename(file_name)


if __name__ == "__main__":
    os.environ[
        'GOOGLE_APPLICATION_CREDENTIALS'] = "/home/timofey/Documents/Google/Cloud/tym-chud-parcs-lab-aec05e2c4417.json"

    # upload_to_storage()
    # text_to_speech()
    speech_to_text()
