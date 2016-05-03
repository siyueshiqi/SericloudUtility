/**
 * Copyright (C) 2015-2016 OurBeehive(http://ourbeehive.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Project Name: SericloudUtil
 * File Name: FtlUtil.java
 * Package Name: seri.core.io.file
 * 
 * Date: May 2, 2016
 * Author: Sericloud
 * 
 */

package seri.core.io.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seri.core.io.builder.FtlModelClassBuilder;
import seri.core.io.model.JavaClass;

public class FtlUtil {

	static final String PATTERN_STR = "\\$\\{(.+)\\}";
	
	static void generateModelClass(String ftlFileName, String classFileName) throws IOException {
		Pattern pattern = Pattern.compile(PATTERN_STR);
		System.out.println("apttern = " + pattern.pattern());
		
		BufferedReader in = null;
		String line = null;

		try {
			in = new BufferedReader(new FileReader(ftlFileName));

			JavaClass javaClass = new JavaClass(classFileName);
			
			while ((line = in.readLine()) != null) { 
				Matcher matcher = pattern.matcher(line);
				while (matcher.find()){
					javaClass.addAttrItem(matcher.group(1));
				}
			}
			
			String classContent = FtlModelClassBuilder.getInstance().exportClass(javaClass);
			
			FileUtil.writeTextFile(classFileName, classContent, false);
		} finally {
			// try {
			if (in != null) {
				in.close();
			}
			// } catch (Exception ex) {
			// throw new ReadFileException(ex);
			// }
		}
		
	}
	
	public static void main(String[] args) {
		String filePathStr = "src/seri/core/io/test/aFormat.ftl";
		try {
			generateModelClass(filePathStr, "src/seri/core/io/test/ExeEvalMap.java");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}