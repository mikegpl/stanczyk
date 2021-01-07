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
  serialized_options=b'\210\001\001',
  create_key=_descriptor._internal_create_key,
  serialized_pb=b'\n\x0estanczyk.proto\x12\x08stanczyk\"4\n\x0b\x46indRequest\x12\x10\n\x08\x66ileName\x18\x01 \x01(\t\x12\x13\n\x0b\x62\x61se64Image\x18\x02 \x01(\t\">\n\x10\x44\x65tectedFaceData\x12\t\n\x01x\x18\x01 \x01(\x03\x12\t\n\x01y\x18\x02 \x01(\x03\x12\t\n\x01w\x18\x03 \x01(\x03\x12\t\n\x01h\x18\x04 \x01(\x03\"6\n\nFindResult\x12(\n\x04\x64\x61ta\x18\x01 \x03(\x0b\x32\x1a.stanczyk.DetectedFaceData\"v\n\x16\x46indAndExchangeRequest\x12&\n\x07request\x18\x01 \x01(\x0b\x32\x15.stanczyk.FindRequest\x12\x34\n\x10\x64\x65vicesKnowledge\x18\x02 \x01(\x0b\x32\x1a.stanczyk.DevicesKnowledge\"j\n\x15\x46indAndExchangeResult\x12$\n\x06result\x18\x01 \x01(\x0b\x32\x14.stanczyk.FindResult\x12+\n\tknowledge\x18\x02 \x01(\x0b\x32\x18.stanczyk.KnowledgeBatch\"x\n\x0eKnowledgeBatch\x12\x30\n\x0e\x63loudKnowledge\x18\x01 \x01(\x0b\x32\x18.stanczyk.CloudKnowledge\x12\x34\n\x10\x64\x65vicesKnowledge\x18\x02 \x01(\x0b\x32\x1a.stanczyk.DevicesKnowledge\"@\n\x0e\x43loudKnowledge\x12.\n\x04\x64\x61ta\x18\x01 \x03(\x0b\x32 .stanczyk.CloudExecutionMetadata\"m\n\x16\x43loudExecutionMetadata\x12\x0c\n\x04\x64\x61ta\x18\x01 \x01(\t\x12,\n\x0ctaskMetadata\x18\x02 \x01(\x0b\x32\x16.stanczyk.TaskMetadata\x12\x17\n\x0f\x65xecutionTimeMs\x18\x03 \x01(\x03\"C\n\x10\x44\x65vicesKnowledge\x12/\n\x04\x64\x61ta\x18\x01 \x03(\x0b\x32!.stanczyk.DeviceExecutionMetadata\"\xa2\x01\n\x17\x44\x65viceExecutionMetadata\x12@\n\x16\x64\x65viceExecutorMetadata\x18\x01 \x01(\x0b\x32 .stanczyk.DeviceExecutorMetadata\x12,\n\x0ctaskMetadata\x18\x02 \x01(\x0b\x32\x16.stanczyk.TaskMetadata\x12\x17\n\x0f\x65xecutionTimeMs\x18\x03 \x01(\x03\"\x9d\x01\n\x16\x44\x65viceExecutorMetadata\x12\x11\n\tcpuRating\x18\x01 \x01(\x03\x12\x15\n\rnetworkRating\x18\x02 \x01(\x03\x12\x17\n\x0fmemoryAvailable\x18\x03 \x01(\x03\x12\x13\n\x0btotalMemory\x18\x04 \x01(\x03\x12\x10\n\x08sdkScore\x18\x05 \x01(\x03\x12\x19\n\x11\x62\x61tteryPercentage\x18\x06 \x01(\x01\"#\n\x0cTaskMetadata\x12\x13\n\x0bproblemSize\x18\x01 \x01(\x03\x32\xc0\x01\n\x1cStanczykTaskExecutionService\x12:\n\tFindFaces\x12\x15.stanczyk.FindRequest\x1a\x14.stanczyk.FindResult\"\x00\x12\x64\n\x1d\x46indFacesAndExchangeKnowledge\x12 .stanczyk.FindAndExchangeRequest\x1a\x1f.stanczyk.FindAndExchangeResult\"\x00\x32o\n StanczykKnowledgeExchangeService\x12K\n\x11\x45xchangeKnowledge\x12\x1a.stanczyk.DevicesKnowledge\x1a\x18.stanczyk.KnowledgeBatch\"\x00\x42\x03\x88\x01\x01\x62\x06proto3'
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
      name='devicesKnowledge', full_name='stanczyk.FindAndExchangeRequest.devicesKnowledge', index=1,
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
  serialized_end=320,
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
      name='knowledge', full_name='stanczyk.FindAndExchangeResult.knowledge', index=1,
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
  serialized_start=322,
  serialized_end=428,
)


_KNOWLEDGEBATCH = _descriptor.Descriptor(
  name='KnowledgeBatch',
  full_name='stanczyk.KnowledgeBatch',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='cloudKnowledge', full_name='stanczyk.KnowledgeBatch.cloudKnowledge', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='devicesKnowledge', full_name='stanczyk.KnowledgeBatch.devicesKnowledge', index=1,
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
  serialized_start=430,
  serialized_end=550,
)


_CLOUDKNOWLEDGE = _descriptor.Descriptor(
  name='CloudKnowledge',
  full_name='stanczyk.CloudKnowledge',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='data', full_name='stanczyk.CloudKnowledge.data', index=0,
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
  serialized_start=552,
  serialized_end=616,
)


_CLOUDEXECUTIONMETADATA = _descriptor.Descriptor(
  name='CloudExecutionMetadata',
  full_name='stanczyk.CloudExecutionMetadata',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='data', full_name='stanczyk.CloudExecutionMetadata.data', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='taskMetadata', full_name='stanczyk.CloudExecutionMetadata.taskMetadata', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='executionTimeMs', full_name='stanczyk.CloudExecutionMetadata.executionTimeMs', index=2,
      number=3, type=3, cpp_type=2, label=1,
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
  serialized_start=618,
  serialized_end=727,
)


_DEVICESKNOWLEDGE = _descriptor.Descriptor(
  name='DevicesKnowledge',
  full_name='stanczyk.DevicesKnowledge',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='data', full_name='stanczyk.DevicesKnowledge.data', index=0,
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
  serialized_start=729,
  serialized_end=796,
)


_DEVICEEXECUTIONMETADATA = _descriptor.Descriptor(
  name='DeviceExecutionMetadata',
  full_name='stanczyk.DeviceExecutionMetadata',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='deviceExecutorMetadata', full_name='stanczyk.DeviceExecutionMetadata.deviceExecutorMetadata', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='taskMetadata', full_name='stanczyk.DeviceExecutionMetadata.taskMetadata', index=1,
      number=2, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='executionTimeMs', full_name='stanczyk.DeviceExecutionMetadata.executionTimeMs', index=2,
      number=3, type=3, cpp_type=2, label=1,
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
  serialized_start=799,
  serialized_end=961,
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
      name='cpuRating', full_name='stanczyk.DeviceExecutorMetadata.cpuRating', index=0,
      number=1, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='networkRating', full_name='stanczyk.DeviceExecutorMetadata.networkRating', index=1,
      number=2, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='memoryAvailable', full_name='stanczyk.DeviceExecutorMetadata.memoryAvailable', index=2,
      number=3, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='totalMemory', full_name='stanczyk.DeviceExecutorMetadata.totalMemory', index=3,
      number=4, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='sdkScore', full_name='stanczyk.DeviceExecutorMetadata.sdkScore', index=4,
      number=5, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR,  create_key=_descriptor._internal_create_key),
    _descriptor.FieldDescriptor(
      name='batteryPercentage', full_name='stanczyk.DeviceExecutorMetadata.batteryPercentage', index=5,
      number=6, type=1, cpp_type=5, label=1,
      has_default_value=False, default_value=float(0),
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
  serialized_start=964,
  serialized_end=1121,
)


_TASKMETADATA = _descriptor.Descriptor(
  name='TaskMetadata',
  full_name='stanczyk.TaskMetadata',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  create_key=_descriptor._internal_create_key,
  fields=[
    _descriptor.FieldDescriptor(
      name='problemSize', full_name='stanczyk.TaskMetadata.problemSize', index=0,
      number=1, type=3, cpp_type=2, label=1,
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
  serialized_start=1123,
  serialized_end=1158,
)

_FINDRESULT.fields_by_name['data'].message_type = _DETECTEDFACEDATA
_FINDANDEXCHANGEREQUEST.fields_by_name['request'].message_type = _FINDREQUEST
_FINDANDEXCHANGEREQUEST.fields_by_name['devicesKnowledge'].message_type = _DEVICESKNOWLEDGE
_FINDANDEXCHANGERESULT.fields_by_name['result'].message_type = _FINDRESULT
_FINDANDEXCHANGERESULT.fields_by_name['knowledge'].message_type = _KNOWLEDGEBATCH
_KNOWLEDGEBATCH.fields_by_name['cloudKnowledge'].message_type = _CLOUDKNOWLEDGE
_KNOWLEDGEBATCH.fields_by_name['devicesKnowledge'].message_type = _DEVICESKNOWLEDGE
_CLOUDKNOWLEDGE.fields_by_name['data'].message_type = _CLOUDEXECUTIONMETADATA
_CLOUDEXECUTIONMETADATA.fields_by_name['taskMetadata'].message_type = _TASKMETADATA
_DEVICESKNOWLEDGE.fields_by_name['data'].message_type = _DEVICEEXECUTIONMETADATA
_DEVICEEXECUTIONMETADATA.fields_by_name['deviceExecutorMetadata'].message_type = _DEVICEEXECUTORMETADATA
_DEVICEEXECUTIONMETADATA.fields_by_name['taskMetadata'].message_type = _TASKMETADATA
DESCRIPTOR.message_types_by_name['FindRequest'] = _FINDREQUEST
DESCRIPTOR.message_types_by_name['DetectedFaceData'] = _DETECTEDFACEDATA
DESCRIPTOR.message_types_by_name['FindResult'] = _FINDRESULT
DESCRIPTOR.message_types_by_name['FindAndExchangeRequest'] = _FINDANDEXCHANGEREQUEST
DESCRIPTOR.message_types_by_name['FindAndExchangeResult'] = _FINDANDEXCHANGERESULT
DESCRIPTOR.message_types_by_name['KnowledgeBatch'] = _KNOWLEDGEBATCH
DESCRIPTOR.message_types_by_name['CloudKnowledge'] = _CLOUDKNOWLEDGE
DESCRIPTOR.message_types_by_name['CloudExecutionMetadata'] = _CLOUDEXECUTIONMETADATA
DESCRIPTOR.message_types_by_name['DevicesKnowledge'] = _DEVICESKNOWLEDGE
DESCRIPTOR.message_types_by_name['DeviceExecutionMetadata'] = _DEVICEEXECUTIONMETADATA
DESCRIPTOR.message_types_by_name['DeviceExecutorMetadata'] = _DEVICEEXECUTORMETADATA
DESCRIPTOR.message_types_by_name['TaskMetadata'] = _TASKMETADATA
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

KnowledgeBatch = _reflection.GeneratedProtocolMessageType('KnowledgeBatch', (_message.Message,), {
  'DESCRIPTOR' : _KNOWLEDGEBATCH,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.KnowledgeBatch)
  })
_sym_db.RegisterMessage(KnowledgeBatch)

CloudKnowledge = _reflection.GeneratedProtocolMessageType('CloudKnowledge', (_message.Message,), {
  'DESCRIPTOR' : _CLOUDKNOWLEDGE,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.CloudKnowledge)
  })
_sym_db.RegisterMessage(CloudKnowledge)

CloudExecutionMetadata = _reflection.GeneratedProtocolMessageType('CloudExecutionMetadata', (_message.Message,), {
  'DESCRIPTOR' : _CLOUDEXECUTIONMETADATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.CloudExecutionMetadata)
  })
_sym_db.RegisterMessage(CloudExecutionMetadata)

DevicesKnowledge = _reflection.GeneratedProtocolMessageType('DevicesKnowledge', (_message.Message,), {
  'DESCRIPTOR' : _DEVICESKNOWLEDGE,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.DevicesKnowledge)
  })
_sym_db.RegisterMessage(DevicesKnowledge)

DeviceExecutionMetadata = _reflection.GeneratedProtocolMessageType('DeviceExecutionMetadata', (_message.Message,), {
  'DESCRIPTOR' : _DEVICEEXECUTIONMETADATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.DeviceExecutionMetadata)
  })
_sym_db.RegisterMessage(DeviceExecutionMetadata)

DeviceExecutorMetadata = _reflection.GeneratedProtocolMessageType('DeviceExecutorMetadata', (_message.Message,), {
  'DESCRIPTOR' : _DEVICEEXECUTORMETADATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.DeviceExecutorMetadata)
  })
_sym_db.RegisterMessage(DeviceExecutorMetadata)

TaskMetadata = _reflection.GeneratedProtocolMessageType('TaskMetadata', (_message.Message,), {
  'DESCRIPTOR' : _TASKMETADATA,
  '__module__' : 'stanczyk_pb2'
  # @@protoc_insertion_point(class_scope:stanczyk.TaskMetadata)
  })
_sym_db.RegisterMessage(TaskMetadata)


DESCRIPTOR._options = None

_STANCZYKTASKEXECUTIONSERVICE = _descriptor.ServiceDescriptor(
  name='StanczykTaskExecutionService',
  full_name='stanczyk.StanczykTaskExecutionService',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_start=1161,
  serialized_end=1353,
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


_STANCZYKKNOWLEDGEEXCHANGESERVICE = _descriptor.ServiceDescriptor(
  name='StanczykKnowledgeExchangeService',
  full_name='stanczyk.StanczykKnowledgeExchangeService',
  file=DESCRIPTOR,
  index=1,
  serialized_options=None,
  create_key=_descriptor._internal_create_key,
  serialized_start=1355,
  serialized_end=1466,
  methods=[
  _descriptor.MethodDescriptor(
    name='ExchangeKnowledge',
    full_name='stanczyk.StanczykKnowledgeExchangeService.ExchangeKnowledge',
    index=0,
    containing_service=None,
    input_type=_DEVICESKNOWLEDGE,
    output_type=_KNOWLEDGEBATCH,
    serialized_options=None,
    create_key=_descriptor._internal_create_key,
  ),
])
_sym_db.RegisterServiceDescriptor(_STANCZYKKNOWLEDGEEXCHANGESERVICE)

DESCRIPTOR.services_by_name['StanczykKnowledgeExchangeService'] = _STANCZYKKNOWLEDGEEXCHANGESERVICE

# @@protoc_insertion_point(module_scope)
