# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: stanczyk.proto
"""Generated protocol buffer code."""
from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='stanczyk.proto',
  package='stanczyk',
  syntax='proto3',
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\x0estanczyk.proto\x12\x08stanczyk\"\"\n\x0fStanczykRequest\x12\x0f\n\x07message\x18\x01 \x01(\t\"?\n\x13\x46\x61\x63\x65\x44\x65tectionResult\x12(\n\x04\x64\x61ta\x18\x01 \x03(\x0b\x32\x1a.stanczyk.DetectedFaceData\">\n\x10\x44\x65tectedFaceData\x12\t\n\x01x\x18\x01 \x01(\x12\x12\t\n\x01y\x18\x02 \x01(\x03\x12\t\n\x01w\x18\x03 \x01(\x03\x12\t\n\x01h\x18\x04 \x01(\x03\"#\n\x10StanczykResponse\x12\x0f\n\x07message\x18\x01 \x01(\t2Y\n\x0eStanczykServer\x12G\n\tFindFaces\x12\x19.stanczyk.StanczykRequest\x1a\x1d.stanczyk.FaceDetectionResult\"\x00\x62\x06proto3'
)




_STANCZYKREQUEST = _descriptor.Descriptor(
  name='StanczykRequest',
  full_name='stanczyk.StanczykRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='message', full_name='stanczyk.StanczykRequest.message', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=28,
  serialized_end=62,
)


_FACEDETECTIONRESULT = _descriptor.Descriptor(
  name='FaceDetectionResult',
  full_name='stanczyk.FaceDetectionResult',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='data', full_name='stanczyk.FaceDetectionResult.data', index=0,
      number=1, type=11, cpp_type=10, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=64,
  serialized_end=127,
)


_DETECTEDFACEDATA = _descriptor.Descriptor(
  name='DetectedFaceData',
  full_name='stanczyk.DetectedFaceData',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='x', full_name='stanczyk.DetectedFaceData.x', index=0,
      number=1, type=18, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='y', full_name='stanczyk.DetectedFaceData.y', index=1,
      number=2, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='w', full_name='stanczyk.DetectedFaceData.w', index=2,
      number=3, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='h', full_name='stanczyk.DetectedFaceData.h', index=3,
      number=4, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=129,
  serialized_end=191,
)


_STANCZYKRESPONSE = _descriptor.Descriptor(
  name='StanczykResponse',
  full_name='stanczyk.StanczykResponse',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='message', full_name='stanczyk.StanczykResponse.message', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=193,
  serialized_end=228,
)

_FACEDETECTIONRESULT.fields_by_name['data'].message_type = _DETECTEDFACEDATA
DESCRIPTOR.message_types_by_name['StanczykRequest'] = _STANCZYKREQUEST
DESCRIPTOR.message_types_by_name['FaceDetectionResult'] = _FACEDETECTIONRESULT
DESCRIPTOR.message_types_by_name['DetectedFaceData'] = _DETECTEDFACEDATA
DESCRIPTOR.message_types_by_name['StanczykResponse'] = _STANCZYKRESPONSE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

StanczykRequest = _reflection.GeneratedProtocolMessageType('StanczykRequest', (_message.Message,), {
  'DESCRIPTOR' : _STANCZYKREQUEST,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.StanczykRequest)
  })
_sym_db.RegisterMessage(StanczykRequest)

FaceDetectionResult = _reflection.GeneratedProtocolMessageType('FaceDetectionResult', (_message.Message,), {
  'DESCRIPTOR' : _FACEDETECTIONRESULT,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.FaceDetectionResult)
  })
_sym_db.RegisterMessage(FaceDetectionResult)

DetectedFaceData = _reflection.GeneratedProtocolMessageType('DetectedFaceData', (_message.Message,), {
  'DESCRIPTOR' : _DETECTEDFACEDATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.DetectedFaceData)
  })
_sym_db.RegisterMessage(DetectedFaceData)

StanczykResponse = _reflection.GeneratedProtocolMessageType('StanczykResponse', (_message.Message,), {
  'DESCRIPTOR' : _STANCZYKRESPONSE,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.StanczykResponse)
  })
_sym_db.RegisterMessage(StanczykResponse)



_STANCZYKSERVER = _descriptor.ServiceDescriptor(
  name='StanczykServer',
  full_name='stanczyk.StanczykServer',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_start=230,
  serialized_end=319,
  methods=[
  _descriptor.MethodDescriptor(
    name='FindFaces',
    full_name='stanczyk.StanczykServer.FindFaces',
    index=0,
    containing_service=None,
    input_type=_STANCZYKREQUEST,
    output_type=_FACEDETECTIONRESULT,
    serialized_options=None,
    create_key=_descriptor._internal_create_key,
  ),
])
_sym_db.RegisterServiceDescriptor(_STANCZYKSERVER)

DESCRIPTOR.services_by_name['StanczykServer'] = _STANCZYKSERVER

# @@protoc_insertion_point(module_scope)
