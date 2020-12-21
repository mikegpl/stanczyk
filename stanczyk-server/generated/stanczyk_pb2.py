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
  serialized_pb=b'\n\x0estanczyk.proto\x12\x08stanczyk\"4\n\x0b\x46indRequest\x12\x10\n\x08\x66ileName\x18\x01 \x01(\t\x12\x13\n\x0b\x62\x61se64Image\x18\x02 \x01(\t\">\n\x10\x44\x65tectedFaceData\x12\t\n\x01x\x18\x01 \x01(\x03\x12\t\n\x01y\x18\x02 \x01(\x03\x12\t\n\x01w\x18\x03 \x01(\x03\x12\t\n\x01h\x18\x04 \x01(\x03\"6\n\nFindResult\x12(\n\x04\x64\x61ta\x18\x01 \x03(\x0b\x32\x1a.stanczyk.DetectedFaceData\"p\n\x16\x46indAndExchangeRequest\x12&\n\x07request\x18\x01 \x01(\x0b\x32\x15.stanczyk.FindRequest\x12.\n\x04meta\x18\x02 \x01(\x0b\x32 .stanczyk.DeviceExecutorMetadata\"l\n\x15\x46indAndExchangeResult\x12$\n\x06result\x18\x01 \x01(\x0b\x32\x14.stanczyk.FindResult\x12-\n\x04meta\x18\x02 \x01(\x0b\x32\x1f.stanczyk.CloudExecutorMetadata\"&\n\x16\x44\x65viceExecutorMetadata\x12\x0c\n\x04\x64\x61ta\x18\x01 \x01(\t\"%\n\x15\x43loudExecutorMetadata\x12\x0c\n\x04\x64\x61ta\x18\x01 \x01(\t2|\n StanczykKnowledgeExchangeService\x12X\n\x11\x45xchangeKnowledge\x12 .stanczyk.DeviceExecutorMetadata\x1a\x1f.stanczyk.CloudExecutorMetadata\"\x00\x32\xc0\x01\n\x1cStanczykTaskExecutionService\x12:\n\tFindFaces\x12\x15.stanczyk.FindRequest\x1a\x14.stanczyk.FindResult\"\x00\x12\x64\n\x1d\x46indFacesAndExchangeKnowledge\x12 .stanczyk.FindAndExchangeRequest\x1a\x1f.stanczyk.FindAndExchangeResult\"\x00\x62\x06proto3'
)




_FINDREQUEST = _descriptor.Descriptor(
  name='FindRequest',
  full_name='stanczyk.FindRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='fileName', full_name='stanczyk.FindRequest.fileName', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='base64Image', full_name='stanczyk.FindRequest.base64Image', index=1,
      number=2, type=9, cpp_type=9, label=1,
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
  serialized_end=80,
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
      number=1, type=3, cpp_type=2, label=1,
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
  serialized_start=82,
  serialized_end=144,
)


_FINDRESULT = _descriptor.Descriptor(
  name='FindResult',
  full_name='stanczyk.FindResult',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='data', full_name='stanczyk.FindResult.data', index=0,
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
  serialized_start=146,
  serialized_end=200,
)


_FINDANDEXCHANGEREQUEST = _descriptor.Descriptor(
  name='FindAndExchangeRequest',
  full_name='stanczyk.FindAndExchangeRequest',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='request', full_name='stanczyk.FindAndExchangeRequest.request', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='meta', full_name='stanczyk.FindAndExchangeRequest.meta', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
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
  serialized_start=202,
  serialized_end=314,
)


_FINDANDEXCHANGERESULT = _descriptor.Descriptor(
  name='FindAndExchangeResult',
  full_name='stanczyk.FindAndExchangeResult',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='result', full_name='stanczyk.FindAndExchangeResult.result', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='meta', full_name='stanczyk.FindAndExchangeResult.meta', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
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
  serialized_start=316,
  serialized_end=424,
)


_DEVICEEXECUTORMETADATA = _descriptor.Descriptor(
  name='DeviceExecutorMetadata',
  full_name='stanczyk.DeviceExecutorMetadata',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='data', full_name='stanczyk.DeviceExecutorMetadata.data', index=0,
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
  serialized_start=426,
  serialized_end=464,
)


_CLOUDEXECUTORMETADATA = _descriptor.Descriptor(
  name='CloudExecutorMetadata',
  full_name='stanczyk.CloudExecutorMetadata',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='data', full_name='stanczyk.CloudExecutorMetadata.data', index=0,
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
  serialized_start=466,
  serialized_end=503,
)

_FINDRESULT.fields_by_name['data'].message_type = _DETECTEDFACEDATA
_FINDANDEXCHANGEREQUEST.fields_by_name['request'].message_type = _FINDREQUEST
_FINDANDEXCHANGEREQUEST.fields_by_name['meta'].message_type = _DEVICEEXECUTORMETADATA
_FINDANDEXCHANGERESULT.fields_by_name['result'].message_type = _FINDRESULT
_FINDANDEXCHANGERESULT.fields_by_name['meta'].message_type = _CLOUDEXECUTORMETADATA
DESCRIPTOR.message_types_by_name['FindRequest'] = _FINDREQUEST
DESCRIPTOR.message_types_by_name['DetectedFaceData'] = _DETECTEDFACEDATA
DESCRIPTOR.message_types_by_name['FindResult'] = _FINDRESULT
DESCRIPTOR.message_types_by_name['FindAndExchangeRequest'] = _FINDANDEXCHANGEREQUEST
DESCRIPTOR.message_types_by_name['FindAndExchangeResult'] = _FINDANDEXCHANGERESULT
DESCRIPTOR.message_types_by_name['DeviceExecutorMetadata'] = _DEVICEEXECUTORMETADATA
DESCRIPTOR.message_types_by_name['CloudExecutorMetadata'] = _CLOUDEXECUTORMETADATA
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

FindRequest = _reflection.GeneratedProtocolMessageType('FindRequest', (_message.Message,), {
  'DESCRIPTOR' : _FINDREQUEST,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.FindRequest)
  })
_sym_db.RegisterMessage(FindRequest)

DetectedFaceData = _reflection.GeneratedProtocolMessageType('DetectedFaceData', (_message.Message,), {
  'DESCRIPTOR' : _DETECTEDFACEDATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.DetectedFaceData)
  })
_sym_db.RegisterMessage(DetectedFaceData)

FindResult = _reflection.GeneratedProtocolMessageType('FindResult', (_message.Message,), {
  'DESCRIPTOR' : _FINDRESULT,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.FindResult)
  })
_sym_db.RegisterMessage(FindResult)

FindAndExchangeRequest = _reflection.GeneratedProtocolMessageType('FindAndExchangeRequest', (_message.Message,), {
  'DESCRIPTOR' : _FINDANDEXCHANGEREQUEST,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.FindAndExchangeRequest)
  })
_sym_db.RegisterMessage(FindAndExchangeRequest)

FindAndExchangeResult = _reflection.GeneratedProtocolMessageType('FindAndExchangeResult', (_message.Message,), {
  'DESCRIPTOR' : _FINDANDEXCHANGERESULT,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.FindAndExchangeResult)
  })
_sym_db.RegisterMessage(FindAndExchangeResult)

DeviceExecutorMetadata = _reflection.GeneratedProtocolMessageType('DeviceExecutorMetadata', (_message.Message,), {
  'DESCRIPTOR' : _DEVICEEXECUTORMETADATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.DeviceExecutorMetadata)
  })
_sym_db.RegisterMessage(DeviceExecutorMetadata)

CloudExecutorMetadata = _reflection.GeneratedProtocolMessageType('CloudExecutorMetadata', (_message.Message,), {
  'DESCRIPTOR' : _CLOUDEXECUTORMETADATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.CloudExecutorMetadata)
  })
_sym_db.RegisterMessage(CloudExecutorMetadata)



_STANCZYKKNOWLEDGEEXCHANGESERVICE = _descriptor.ServiceDescriptor(
  name='StanczykKnowledgeExchangeService',
  full_name='stanczyk.StanczykKnowledgeExchangeService',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_start=505,
  serialized_end=629,
  methods=[
  _descriptor.MethodDescriptor(
    name='ExchangeKnowledge',
    full_name='stanczyk.StanczykKnowledgeExchangeService.ExchangeKnowledge',
    index=0,
    containing_service=None,
    input_type=_DEVICEEXECUTORMETADATA,
    output_type=_CLOUDEXECUTORMETADATA,
    serialized_options=None,
    create_key=_descriptor._internal_create_key,
  ),
])
_sym_db.RegisterServiceDescriptor(_STANCZYKKNOWLEDGEEXCHANGESERVICE)

DESCRIPTOR.services_by_name['StanczykKnowledgeExchangeService'] = _STANCZYKKNOWLEDGEEXCHANGESERVICE


_STANCZYKTASKEXECUTIONSERVICE = _descriptor.ServiceDescriptor(
  name='StanczykTaskExecutionService',
  full_name='stanczyk.StanczykTaskExecutionService',
  file=DESCRIPTOR,
  index=1,
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_start=632,
  serialized_end=824,
  methods=[
  _descriptor.MethodDescriptor(
    name='FindFaces',
    full_name='stanczyk.StanczykTaskExecutionService.FindFaces',
    index=0,
    containing_service=None,
    input_type=_FINDREQUEST,
    output_type=_FINDRESULT,
    serialized_options=None,
    create_key=_descriptor._internal_create_key,
  ),
  _descriptor.MethodDescriptor(
    name='FindFacesAndExchangeKnowledge',
    full_name='stanczyk.StanczykTaskExecutionService.FindFacesAndExchangeKnowledge',
    index=1,
    containing_service=None,
    input_type=_FINDANDEXCHANGEREQUEST,
    output_type=_FINDANDEXCHANGERESULT,
    serialized_options=None,
    create_key=_descriptor._internal_create_key,
  ),
])
_sym_db.RegisterServiceDescriptor(_STANCZYKTASKEXECUTIONSERVICE)

DESCRIPTOR.services_by_name['StanczykTaskExecutionService'] = _STANCZYKTASKEXECUTIONSERVICE

# @@protoc_insertion_point(module_scope)