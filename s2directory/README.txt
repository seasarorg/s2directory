# 注意事項

s2-directory/src/test/resources と s2-directory-examples/src/test/resources に
directory.dicon が重複しているのは、 mvn test 時に必要になるためです。

# テスト方法

mvn test

# ローカルビルド

次のコマンドで target/ 以下に jarファイル が作成される

mvn clean package 

- テストを実行しない場合

mvn -Dmaven.test.skip=true clean package

# Mavenデプロイ

mvn clean deploy

# リリース手順

次のコマンドで target/ 以下に zipファイル が作成される

mvn clean package
ant dist
