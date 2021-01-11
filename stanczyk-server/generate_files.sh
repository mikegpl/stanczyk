python3 -m grpc_tools.protoc \
        -I ../proto/ \
        --python_out=./generated \
        --grpc_python_out=./generated \
        ../proto/*.proto

sed -i -E 's/^import.*_pb2/from . \0/' ./generated/*.py